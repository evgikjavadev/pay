//package ru.vtb.msa.rfrm.integration.internalkafka;
//
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaTemplateConfigStatuses {
//
//    @Bean
//    public KafkaTemplate kafkaTemplate() {
//        return new KafkaTemplate(producerFactoryStatuses());
//    }
//    @Bean
//    public ProducerFactory producerFactoryStatuses() {
//        Map<String, Object> configMap = new HashMap<>();
//        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        return new DefaultKafkaProducerFactory(configMap);
//
//    }
//}
