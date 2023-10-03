package ru.vtb.msa.rfrm.integration.rfrmkafka.processing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.integration.internalkafka.InternalProcessingTasksStatuses;
import ru.vtb.msa.rfrm.integration.internalkafka.InternalProcessingTasksPayment;
import ru.vtb.msa.rfrm.integration.rfrmkafka.mapper.QuestionnairesMapper;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.service.ProcessQuestionnairesService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumerCoreClient {
    private final ProcessQuestionnairesService service;
    private final InternalProcessingTasksStatuses internalProcessingTasksStatuses;
    private final InternalProcessingTasksPayment internalProcessingTasksPayment;

    @KafkaListener(id = "${process.platformpay.kafka.consumer.group-id}",
                   topics = "${process.platformpay.kafka.topic.rfrm_core_payment_order}",
                   containerFactory = "kafkaListenerContainerFactoryReward"
    )
    public void listenRfrmCore(@Payload List<QuestionnairesKafkaModel> messageList, Acknowledgment ack,
                             @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                             @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                             @Header(KafkaHeaders.OFFSET) int offsets) throws InterruptedException {
        log.info("Start rfrm-pay processing topic = {}, partition = {}, messages = {}", topic, partition, messageList);

        try {
            service.validateFieldsAndSaveTaskToDB(messageList);
            ack.acknowledge();
        } catch (Exception e) {
            ack.nack(0,100);
            log.error(e.getMessage(), e.fillInStackTrace());
        }

        log.info("Finish rfrm-pay processing topic = {} partition = {}", topic, partition);


        internalProcessingTasksStatuses.processInternalKafkaStatus();
        //internalProcessingTasksPayment.sendRunningMessageInternalTopic();

    }
}
