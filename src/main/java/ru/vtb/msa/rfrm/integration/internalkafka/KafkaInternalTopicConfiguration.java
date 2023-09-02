//package ru.vtb.msa.rfrm.integration.internalkafka;
//
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.KafkaAdmin;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaInternalTopicConfiguration {
//
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> congigMap = new HashMap<>();
//        congigMap.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        return new KafkaAdmin(congigMap);
//    }
//
////    @Bean
////    public NewTopic newTopic() {
////        return new NewTopic("rfrm_pay_function_status_update_reward", 1, (short) 1);
////    }
//
//
//}
