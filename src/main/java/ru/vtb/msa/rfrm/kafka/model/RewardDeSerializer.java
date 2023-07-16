package ru.vtb.msa.rfrm.kafka.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

@Component
public class RewardDeSerializer implements Deserializer<ObjectRewardReqDeser> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ObjectRewardReqDeser deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(s, ObjectRewardReqDeser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}