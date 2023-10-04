package ru.vtb.msa.rfrm.integration.internalkafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import ru.vtb.msa.rfrm.integration.internalkafka.model.InternalMessageModel;

@RequiredArgsConstructor
public class KafkaInternalProducer {

    private final KafkaTemplate<String, InternalMessageModel> kafkaTemplate;

    public void sendObject(String topic, InternalMessageModel model) {
        kafkaTemplate.send(topic, model);
        kafkaTemplate.flush();
    }

}
