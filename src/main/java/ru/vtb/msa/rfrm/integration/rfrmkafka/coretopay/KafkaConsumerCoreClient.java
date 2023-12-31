package ru.vtb.msa.rfrm.integration.rfrmkafka.coretopay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.CorePayKafkaModel;
import ru.vtb.msa.rfrm.functions.ProcessQuestionnairesService;
import ru.vtb.msa.rfrm.service.PrepareProcessGetAccounts;

import java.util.List;

/** Класс консьюмера для функции ПЗ. Получение заданий на оплату и сохранение их в БД */
@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumerCoreClient {
    private final ProcessQuestionnairesService service;
    private final PrepareProcessGetAccounts prepareProcessGetAccounts;

    @KafkaListener(id = "${core.kafka.consumer.group-id-core}",
                   topics = "${core.kafka.topic}",
                   containerFactory = "kafkaListenerContainerFactoryReward")
    public void listenRfrmCore(@Payload List<CorePayKafkaModel> messageList, Acknowledgment ack,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                               @Header(KafkaHeaders.OFFSET) int offsets) {
        log.info("Start rfrm-pay processing topic = {}, partition = {}, messages = {}", topic, partition, messageList);

        try {
            // валидация полей, запись в БД задания на оплату, вызов прод профиля если совпадений по rewardId не найдено
            service.validateFieldsAndSaveTaskToDB(messageList);

            // вызов прод профиля 1503
            //prepareProcessGetAccounts.firstStepProcessAccounts(messageList);

            ack.acknowledge();
        } catch (Exception e) {
            ack.nack(0,100);
            log.error(e.getMessage(), e.fillInStackTrace());
        }

        log.info("Finish rfrm-pay processing topic = {} partition = {}", topic, partition);

        //internalProcessingTasksStatuses.processInternalKafkaStatus();
        //internalProcessingTasksPayment.sendRunningMessageInternalTopic();

    }
}
