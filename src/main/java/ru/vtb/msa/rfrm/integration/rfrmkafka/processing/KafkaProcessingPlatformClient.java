package ru.vtb.msa.rfrm.integration.rfrmkafka.processing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import ru.vtb.msa.rfrm.integration.rfrmkafka.mapper.QuestionnairesMapper;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.service.FirstProcessQuestionnairesService;


import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class KafkaProcessingPlatformClient {

    private final FirstProcessQuestionnairesService service;

    private final QuestionnairesMapper mapper;


    @KafkaListener(id = "${process.platform.kafka.consumer.group-id}",
            topics = "${process.platform.kafka.topic.questionnaires}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenPEN_RE(@Payload List<QuestionnairesKafkaModel> messageList, Acknowledgment ack,
                             @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                             @Header(KafkaHeaders.OFFSET) int offsets) {

        for (int i = 0; i < messageList.size(); i++) {
            //log.info("index = {}, dataProcess = {}, offset = {}, par = {}", messageList.get(i).toString(), offsets, partition);
            //QuestionnairesModel model = mapper.quesKafkaToQuesModel(messageList.get(i));
            //service.firstProcessQuestionnaires(model);
            service.firstProcessQuestionnaires(messageList.get(i));
            ack.acknowledge();
            log.info("commit offset index = {}, offset = {}, par = {}", i, offsets, partition);
        }

    }
}
