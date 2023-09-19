package ru.vtb.msa.rfrm.integration.internalkafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.msa.rfrm.integration.internalkafka.model.InternalMessageModel;
import ru.vtb.msa.rfrm.integration.util.enums.Statuses;
import ru.vtb.msa.rfrm.processingDatabase.batch.ActionEntPaymentTaskRepo;
import ru.vtb.msa.rfrm.processingDatabase.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.repository.EntPaymentTaskRepository;
import ru.vtb.msa.rfrm.service.ServiceAccounts;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/** Класс Сценария обработки новых заданий на оплату */
@Component
@RequiredArgsConstructor
@Slf4j
public class InternalProcessingTasksPayment {
    private final EntPaymentTaskRepository entPaymentTaskRepository;
    private final KafkaInternalConfigProperties kafkaInternalConfigProperties;
    private final ActionEntPaymentTaskRepo actionEntPaymentTaskRepo;
    private final ServiceAccounts serviceAccounts;
    @Value("${process.platformpay.kafka.topic.rfrm_pay_function_result_reward}")
    private String RFRM_PAY_FUNCTION_RESULT_REWARD;
    @Value("${process.platformpay.kafka.listener.poll-timeout}")
    private Long POLL_TIMEOUT_CONSUMER;
    @Value("${ms.properties.findSizeApplication}")
    private Integer findSizeApplication;
    @Value("${sleep-task-payment}")
    private Integer sleepMs;

    @Transactional
    @Scheduled(fixedRate = 3600*1000, initialDelay = 5*1000)
    public void processInternalKafkaTasksPayment() throws InterruptedException {

        Consumer<String, InternalMessageModel> consumer = new KafkaConsumer<>(kafkaInternalConfigProperties.setInternalConsumerProperties());
        consumer.subscribe(Collections.singletonList(RFRM_PAY_FUNCTION_RESULT_REWARD));
        ConsumerRecords<String, InternalMessageModel> records = consumer.poll(Duration.ofMillis(POLL_TIMEOUT_CONSUMER));
        records.forEach(record -> {
                    log.info("Received message from topic RFRM_PAY_FUNCTION_RESULT_REWARD: " + record.value());

                });

        consumer.close();

        // Осуществить поиск N заданий в таблице paymentTask, у которых status=10 (Новая) и blocked=0, с сортировкой по blocked_at возрастанию
        List<EntPaymentTask> entPaymentTaskList = entPaymentTaskRepository.findByStatus(DctTaskStatuses.STATUS_NEW.getStatus(), findSizeApplication);

        // передаем в обработку List задач
        handleMdmIdList(entPaymentTaskList);

        sendRunningMessageInternalTopic();

    }

    private void handleMdmIdList(List<EntPaymentTask> entPaymentTaskList) throws InterruptedException {

        List<UUID> setRewardIdList = entPaymentTaskList.stream().map(EntPaymentTask::getRewardId).distinct().collect(Collectors.toList());

        List<Long> mdmIdsList = entPaymentTaskList.stream().map(EntPaymentTask::getMdmId).collect(Collectors.toList());

        //Установить для задачи blocked=1 и blocked_at=now()
        //actionEntPayTaskRepo.updateBlockByUUIDEqualOne(setRewardIdList);
        actionEntPaymentTaskRepo.updateBlockByUUIDEqualOne(setRewardIdList);

        for (Long mdmId: mdmIdsList ) {
            // Вызов метода /portfolio/active 1503 для каждого mdmId
            serviceAccounts.getClientAccounts(mdmId);
        }

        // Установить для задачи blocked=0 и blocked_at=now()
        actionEntPaymentTaskRepo.updateBlockByUUIDEqualZero(setRewardIdList);

        Thread.sleep(sleepMs);

    }

    public void sendRunningMessageInternalTopic() throws InterruptedException {

        // создание объекта для отправки в internal топик rfrm_pay_function_result_reward
        InternalMessageModel internalMessageModel = getInternalMessageModel();

        KafkaProducer<String, InternalMessageModel> producer = new KafkaProducer<>(kafkaInternalConfigProperties.setInternalProducerProperties());
        ProducerRecord<String, InternalMessageModel> record = new ProducerRecord<>(RFRM_PAY_FUNCTION_RESULT_REWARD, internalMessageModel);
        producer.send(record);
        producer.flush();
        producer.close();

        processInternalKafkaTasksPayment();
    }

    private static InternalMessageModel getInternalMessageModel() {
        return InternalMessageModel
                .builder()
                .functionName("function_status_update_reward")
                .status(Statuses.COMPLETED.name())
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
