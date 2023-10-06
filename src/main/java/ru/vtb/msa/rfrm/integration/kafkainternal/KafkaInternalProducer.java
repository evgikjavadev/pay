package ru.vtb.msa.rfrm.integration.kafkainternal;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import ru.vtb.msa.rfrm.integration.kafkainternal.model.InternalMessageModel;

@RequiredArgsConstructor
public class KafkaInternalProducer {

    private final KafkaTemplate<String, InternalMessageModel> kafkaTemplate;

    public void sendObjectToInternalKafka(String topic, InternalMessageModel model) {
        kafkaTemplate.send(topic, model);
        kafkaTemplate.flush();
    }

}
