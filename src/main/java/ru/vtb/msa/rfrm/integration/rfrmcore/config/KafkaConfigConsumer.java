package ru.vtb.msa.rfrm.integration.rfrmcore.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.vtb.msa.rfrm.integration.rfrmcore.model.ObjectRewardReq;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfigConsumer {

    private String bootstrapServerKafka = "localhost:9092";
    private String groupIdConfig = "group_id";

    @Bean
    public ConsumerFactory<String, ObjectRewardReq> userConsumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerKafka);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdConfig);
        return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(ObjectRewardReq.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ObjectRewardReq> userKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ObjectRewardReq> factory = new ConcurrentKafkaListenerContainerFactory<String, ObjectRewardReq>();
        //factory.setConsumerFactory(userConsumerFactory());
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }
}
