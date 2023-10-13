package ru.vtb.msa.rfrm.integration.kafkainternal;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import ru.vtb.msa.rfrm.integration.kafkainternal.model.InternalMessageModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreKafkaModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.processing.KafkaResultRewardProducer;
import ru.vtb.msa.rfrm.integration.util.enums.Statuses;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.processingDatabase.batch.ActionEntPaymentTaskRepo;
import ru.vtb.msa.rfrm.processingDatabase.model.DctStatusDetails;
import ru.vtb.msa.rfrm.processingDatabase.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.repository.EntPaymentTaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class KafkaInternalConsumerTask {
    @Value("${function.kafka.topic.rfrm_pay_function_status_update_reward}")
    private String rfrm_pay_function_status_update_reward;
    @Value("${findSizeApplication}")
    private Integer findSizeApplication;
    @Value("${sleep-task-status}")
    private Integer sleepMs;
//    @Value("${function.kafka.listener.poll-timeout}")
//    private Long POLL_TIMEOUT_CONSUMER;
    private final EntPaymentTaskActions entPaymentTaskActions;
    private final KafkaResultRewardProducer kafkaResultRewardProducer;
    private final EntPaymentTaskRepository entPaymentTaskRepository;
    private final ActionEntPaymentTaskRepo actionEntPaymentTaskRepo;
    private final KafkaInternalProducer kafkaInternalProducer;

    @SneakyThrows
    @KafkaListener(id = "${function.kafka.consumer.group-id-res}",
            topics = "${function.kafka.topic.rfrm_pay_function_status_update_reward}",
            containerFactory = "kafkaListenerInternalResult")
    public void listenFunctionStatusUpdateReward(@Payload InternalMessageModel message, Acknowledgment ack,
                                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                           @Header(KafkaHeaders.OFFSET) int offsets) {
        log.info("Start rfrm-pay processing topic = {}, partition = {}, messages = {}", topic, partition, message);

        // Выбрать из таблицы ent_payment_task N (количество настраивается в конфиге) заданий с processed=false и blocked=0, с сортировкой по blocked_at по возрастанию
        List<EntPaymentTask> paymentTaskList = entPaymentTaskRepository.getRewardIdsByProcessAndBlocked(findSizeApplication);

        // передаем в обработку задачи
        handleTasksList(paymentTaskList);

        // соберем объект для отправки в internal топик rfrm_pay_function_status_update_reward для запуска задания на обработку
        sendRunningMessageInternalTopic();

        ack.acknowledge();

        log.info("Finish rfrm-pay processing topic = {} partition = {}", topic, partition);
    }

        private void handleTasksList(List<EntPaymentTask> paymentTaskList) {

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
        //Thread.sleep(sleepMs);
    }

    private void sendRunningMessageInternalTopic() {

        InternalMessageModel internalMessageModel = InternalMessageModel
                .builder()
                .functionName("function_result_reward")
                .status(Statuses.COMPLETED.name())
                .timeStamp(LocalDateTime.now())
                .build();

        kafkaInternalProducer.sendObjectToInternalKafka(rfrm_pay_function_status_update_reward, internalMessageModel);
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

}
