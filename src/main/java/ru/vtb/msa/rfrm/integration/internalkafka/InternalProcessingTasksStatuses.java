package ru.vtb.msa.rfrm.integration.internalkafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.msa.rfrm.integration.internalkafka.model.InternalMessageModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.processing.KafkaResultRewardProducer;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreKafkaModel;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.processingDatabase.batch.ActionEntPaymentTaskRepo;
import ru.vtb.msa.rfrm.processingDatabase.model.DctStatusDetails;
import ru.vtb.msa.rfrm.processingDatabase.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.repository.EntPaymentTaskRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** Сценарий обработки статусов заданий на оплату */
@RequiredArgsConstructor
//@Component
@Slf4j
public class InternalProcessingTasksStatuses {
    @Value("${process.platformpay.kafka.topic.rfrm_pay_function_status_update_reward}")
    private String RFRM_PAY_FUNCTION_STATUS_UPDATE_REWARD;
    private final EntPaymentTaskActions entPaymentTaskActions;
    private final KafkaResultRewardProducer kafkaResultRewardProducer;
    private final EntPaymentTaskRepository entPaymentTaskRepository;
    @Value("${ms.properties.findSizeApplication}")
    private Integer findSizeApplication;
    @Value("${sleep-task-status}")
    private Integer sleepMs;
    @Value("${process.platformpay.kafka.listener.poll-timeout}")
    private Long POLL_TIMEOUT_CONSUMER;
    private final ActionEntPaymentTaskRepo actionEntPaymentTaskRepo;
    private final KafkaInternalConfigProperties kafkaInternalConfigProperties;

    @Transactional
    //@Scheduled(fixedRate = 86400*1000, initialDelay = 5*1000)   //читаем сообщения каждые сутки
    public void processInternalKafkaStatus() throws InterruptedException {

        Consumer<String, InternalMessageModel> consumer = new KafkaConsumer<>(kafkaInternalConfigProperties.setInternalConsumerProperties());
        consumer.subscribe(Collections.singletonList(RFRM_PAY_FUNCTION_STATUS_UPDATE_REWARD));
        ConsumerRecords<String, InternalMessageModel> records = consumer.poll(Duration.ofMillis(POLL_TIMEOUT_CONSUMER));
        records.forEach(record -> {
            log.info("Received message from topic RFRM_PAY_FUNCTION_STATUS_UPDATE_REWARD: " + record.value());

        });

        consumer.close();

        // Выбрать из таблицы ent_payment_task N (количество настраивается в конфиге) заданий с processed=false и blocked=0, с сортировкой по blocked_at по возрастанию
        List<EntPaymentTask> paymentTaskList = entPaymentTaskRepository.getRewardIdsByProcessAndBlocked(findSizeApplication);

        // передаем в обработку задачи
        handleTasksList(paymentTaskList);

        // соберем объект для отправки в internal топик rfrm_pay_function_status_update_reward для запуска задания на обработку
        InternalMessageModel internalMessageModel = getInternalMessageModel();
        sendMessage(internalMessageModel);

    }

    private void handleTasksList(List<EntPaymentTask> paymentTaskList) throws InterruptedException {

        List<Integer> setRewardIdList = paymentTaskList.stream().map(EntPaymentTask::getRewardId).distinct().collect(Collectors.toList());

        // Установить для задачи blocked=1 и blocked_at=now()
        actionEntPaymentTaskRepo.updateBlockByRewardIdEqualOne(setRewardIdList);

        for (EntPaymentTask task: paymentTaskList) {

            // Установить для задачи ent_payment_task.processed=true
            entPaymentTaskActions.updateProcessedBPaymentTaskByRewardId(task.getRewardId());

            // Соберем объект для отправки в топик rfrm_pay_result_reward (Core service), указав reward_id = ent_payment_task.reward_id, status=ent_payment_task.status
            PayCoreKafkaModel coreLinkModel = getPayCoreLinkModel(task.getRewardId(), task.getStatus());

            // Отправить сообщение в топик rfrm_pay_result_reward (Core service)
            kafkaResultRewardProducer.sendToResultReward(coreLinkModel);

            // Установить для задачи blocked=0
            actionEntPaymentTaskRepo.updateBlockByRewardIdEqualZero(setRewardIdList);

        }

        Thread.sleep(sleepMs);

    }

    private void sendMessage(InternalMessageModel internalMessageModel) {
        KafkaProducer<String, InternalMessageModel> producer = new KafkaProducer<>(kafkaInternalConfigProperties.setInternalProducerProperties());
        ProducerRecord<String, InternalMessageModel> record = new ProducerRecord<>(RFRM_PAY_FUNCTION_STATUS_UPDATE_REWARD, internalMessageModel);
        producer.send(record);
        log.info("object {} sent successfully to topic {}", internalMessageModel, RFRM_PAY_FUNCTION_STATUS_UPDATE_REWARD);
        producer.flush();
        producer.close();
    }

    private static PayCoreKafkaModel getPayCoreLinkModel(Integer rewardId, Integer status) {

        // status_description = Если ent_payment_task.status = 30, то привести к строке (SELECT description FROM dct_status_details WHERE status_details_code = 203),
        // иначе - поле не отправлять

        if (status.equals(DctTaskStatuses.STATUS_REJECTED.getStatus())) {
            String description = DctStatusDetails.ERR_REQUIREMENTS.getDescription();
            return PayCoreKafkaModel
                    .builder()
                    .rewardId(rewardId)
                    .status(status)
                    .statusDescription(description)
                    .build();
        } else {
            return PayCoreKafkaModel
                    .builder()
                    .rewardId(rewardId)
                    .status(status)
                    .build();
        }

    }

    private static InternalMessageModel getInternalMessageModel() {
        return InternalMessageModel
                .builder()
                .functionName("function_status_update_reward")
                .status("completed")
                .timeStamp(LocalDateTime.now())
                .build();
    }

}
