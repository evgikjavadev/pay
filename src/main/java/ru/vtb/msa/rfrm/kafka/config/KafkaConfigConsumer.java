package ru.vtb.msa.rfrm.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.vtb.msa.rfrm.kafka.model.ObjectRewardReq;
import ru.vtb.msa.rfrm.kafka.model.ObjectRewardReqDeser;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfigConsumer {

    private String bootstrapServerKafka = "localhost:9092";
    private String groupIdConfig = "group_id";

    @Bean
    public ConsumerFactory<String, ObjectRewardReqDeser> userConsumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerKafka);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdConfig);
        return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(ObjectRewardReqDeser.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ObjectRewardReqDeser> userKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ObjectRewardReqDeser> factory = new ConcurrentKafkaListenerContainerFactory<String, ObjectRewardReqDeser>();
        //factory.setConsumerFactory(userConsumerFactory());
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }
}
