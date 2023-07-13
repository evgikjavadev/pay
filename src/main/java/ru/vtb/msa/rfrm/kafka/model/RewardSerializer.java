//package ru.vtb.msa.rfrm.kafka.model;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.serialization.Serializer;
//
//public class RewardSerializer implements Serializer<ObjectRewardReq> {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public byte[] serialize(String topic, ObjectRewardReq reward) {
//        try {
//            return objectMapper.writeValueAsBytes(reward);
//        } catch (Exception e) {
//            throw new RuntimeException("Error serializing Reward object", e);
//        }
//    }
//}