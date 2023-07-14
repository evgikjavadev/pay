package ru.vtb.msa.rfrm.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.kafka.model.ObjectRewardReq;

@Component
public class KafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(topics = "RewardReq", groupId = "group_id")
    public void consume(ObjectRewardReq message) {

        //ConsumerRecord<String, ObjectRewardReq> record = null;
        //Headers headers = record.headers().add("X-Mdm-Id", null);
        System.out.println("my-message = " + message + "message header = " );

    }
}
