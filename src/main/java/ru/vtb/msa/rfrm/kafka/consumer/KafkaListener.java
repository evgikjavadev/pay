package ru.vtb.msa.rfrm.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.kafka.model.ObjectRewardReqDeser;
import ru.vtb.msa.rfrm.kafka.model.RewardDeSerializer;

import java.io.IOException;

@Component
public class KafkaListener {

    private final RewardDeSerializer rewardDeSerializer;

    public KafkaListener(RewardDeSerializer rewardDeSerializer) {
        this.rewardDeSerializer = rewardDeSerializer;
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = "RewardReq", groupId = "group_id")
    public void consume(@Payload String message) throws IOException, InterruptedException {
        //ObjectRewardReq rewardReq = message.wait();
        ObjectMapper objectMapper = new ObjectMapper();

        //ObjectRewardReqDeser objectRewardReqDeser = rewardDeSerializer.deserialize(message.getBytes().toString(), ObjectRewardReqDeser.class);
        //String json = objectMapper.writeValueAsString(message);

        //ObjectRewardReqDeser deserializedObject = objectMapper.readValue(message, ObjectRewardReqDeser.class);

        //ObjectInputStream objectInputStream = new ObjectInputStream();

        // Deserialize the object
        //Object deserializedObject = objectInputStream.readObject();
        RewardDeSerializer objectRewardReq = objectMapper.readValue(message.getBytes(), RewardDeSerializer.class);
        //ConsumerRecord<String, ObjectRewardReq> record = null;
        //Headers headers = record.headers().add("X-Mdm-Id", null);
        System.out.println("my-message = " + objectRewardReq + "    message header = " );

    }
}
