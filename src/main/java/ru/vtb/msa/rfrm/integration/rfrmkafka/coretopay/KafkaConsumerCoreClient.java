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
import ru.vtb.msa.rfrm.integration.rfrmkafka.service.ProcessQuestionnairesService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumerCoreClient {
    private final ProcessQuestionnairesService service;
    //private final InternalProcessingTasksStatuses internalProcessingTasksStatuses;
    //private final InternalProcessingTasksPayment internalProcessingTasksPayment;

    @KafkaListener(id = "${core.kafka.consumer.group-id-core}",
                   topics = "${core.kafka.topic}",
                   containerFactory = "kafkaListenerContainerFactoryReward")
    public void listenRfrmCore(@Payload List<CorePayKafkaModel> messageList, Acknowledgment ack,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                               @Header(KafkaHeaders.OFFSET) int offsets) {
        log.info("Start rfrm-pay processing topic = {}, partition = {}, messages = {}", topic, partition, messageList);

        try {
            service.validateFieldsAndSaveTaskToDB(messageList);
            ack.acknowledge();
        } catch (Exception e) {
            ack.nack(0,100);
            log.error(e.getMessage(), e.fillInStackTrace());
        }

        log.info("Finish rfrm-pay processing topic = {} partition = {}", topic, partition);


        //internalProcessingTasksStatuses.processInternalKafkaStatus();   //todo решить по запуску внутренней кафки
        //internalProcessingTasksPayment.sendRunningMessageInternalTopic();

    }
}
