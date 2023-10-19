package ru.vtb.msa.rfrm.integration.rfrmkafka.coretopay;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.StringUtils;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.CorePayKafkaModel;


import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
public class KafkaCoreConfig {
    private static final String SECURITY_PROTOCOL = "security.protocol";
    @Value("${pay.kafka.consumer.group-id}")
    private String groupId;
    @Value("${pay.kafka.bootstrap.server}")
    private String servers;
    @Value("${pay.kafka.session.timeout.ms:15000}")
    private String sessionTimeout;
    @Value("${pay.kafka.consumer.schedule.time:5000}")
    private long consumerScheduleTime;
    @Value("${pay.kafka.consumer.pool.time:30000}")
    private long consumerPoolTime;
    @Value("${pay.kafka.max.partition.fetch.bytes:300000}")
    private String maxPartitionFetchBytes;
    @Value("${pay.kafka.max.poll.records:500}")
    private String maxPollRecords;
    @Value("${pay.kafka.max.poll.interval.ms:3000}")
    private String maxPollIntervalsMs;

    // для организации "SSL":
    @Value("${pay.kafka.security.protocol:SSL}")
    private String securityProtocol;

    // При securityProtocol = "SSL" обязательны:
    @Value("${pay.kafka.ssl.endpoint.identification.algorithm:}")
    private String sslEendpointIdentificationAlgorithm;
    @Value("${pay.kafka.ssl.truststore.location:}")
    private String sslTruststoreLocation;
    @Value("${pay.kafka.ssl.truststore.password:}")
    private String sslTruststorePassword;
    @Value("${pay.kafka.ssl.keystore.location:}")
    private String sslKeystoreLocation;
    @Value("${pay.kafka.ssl.keystore.password:}")
    private String sslKeystorePassword;
    @Value("${pay.kafka.ssl.key.password:}")
    private String sslKeyPassword;

    // При securityProtocol = "SSL" необязательны:
    @Value("${pay.kafka.ssl.cipher.suites:}")
    private String sslCipherSuites;
    @Value("${pay.kafka.ssl.enabled.protocols:}")
    private String sslEnabledProtocols;
    @Value("${pay.kafka.ssl.keymanager.algorithm:}")
    private String sslKeymanagerAlgorithm;
    @Value("${pay.kafka.ssl.keystore.type:}")
    private String sslKeystoreType;
    @Value("${pay.kafka.ssl.protocol:}")
    private String sslProtocol;
    @Value("${pay.kafka.ssl.provider:}")
    private String sslProvider;
    @Value("${pay.kafka.ssl.secure.random.implementation:}")
    private String sslSecureRandomImplementation;
    @Value("${pay.kafka.ssl.trustmanager.algorithm:}")
    private String sslTrustmanagerAlgorithm;
    @Value("${pay.kafka.ssl.truststore.type:}")
    private String sslTruststoreType;

    @Bean
    public ConsumerFactory<String, CorePayKafkaModel> consumerFactoryCore(KafkaProperties kafkaProp) {
        Map<String, Object> props = kafkaProp.buildConsumerProperties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "ru.vtb.msa.rfrm.integration.rfrmkafka.model.CorePayKafkaModel");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalsMs);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, Boolean.FALSE);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        setSecurityProps(props);

        DefaultKafkaConsumerFactory factory = new DefaultKafkaConsumerFactory<String, CorePayKafkaModel>(props);
        factory.setKeyDeserializer(new StringDeserializer());
        factory.setValueDeserializer(new JsonDeserializer());

        return factory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, CorePayKafkaModel> kafkaListenerContainerFactoryReward(@Qualifier("consumerFactoryCore") ConsumerFactory<String, CorePayKafkaModel> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, CorePayKafkaModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.setConcurrency(1);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        //factory.getContainerProperties().setAsyncAcks(true);
        factory.getContainerProperties().setPollTimeout(500);
        factory.getContainerProperties().setMicrometerEnabled(true);
       // factory.getContainerProperties().setMicrometerTags();

        return factory;
    }

//    @Bean
//    KafkaConsumerCoreClient client(ProcessQuestionnairesService service, InternalProcessingTasksStatuses status, InternalProcessingTasksPayment pay) {
//        //QuestionnairesMapper mapper = Mappers.getMapper(QuestionnairesMapper.class);
//        return new KafkaConsumerCoreClient(service, status, pay);
//    }

    private void setSecurityProps(Map<String, Object> props) {
        setProp(props, SECURITY_PROTOCOL, securityProtocol);

        // При securityProtocol = "SSL" обязательны:
        setSslEndpointIdentificationAlgorithm(props);
        setProp(props, SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, sslTruststoreLocation);
        setProp(props, SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, sslTruststorePassword);
        setProp(props, SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, sslKeystoreLocation);
        setProp(props, SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, sslKeystorePassword);
        setProp(props, SslConfigs.SSL_KEY_PASSWORD_CONFIG, sslKeyPassword);

        // При securityProtocol = "SSL" необязательны:
        setProp(props, SslConfigs.SSL_CIPHER_SUITES_CONFIG, sslCipherSuites);
        setProp(props, SslConfigs.SSL_ENABLED_PROTOCOLS_CONFIG, sslEnabledProtocols);
        setProp(props, SslConfigs.SSL_KEYMANAGER_ALGORITHM_CONFIG, sslKeymanagerAlgorithm);
        setProp(props, SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, sslKeystoreType);
        setProp(props, SslConfigs.SSL_PROTOCOL_CONFIG, sslProtocol);
        setProp(props, SslConfigs.SSL_PROVIDER_CONFIG, sslProvider);
        setProp(props, SslConfigs.SSL_SECURE_RANDOM_IMPLEMENTATION_CONFIG, sslSecureRandomImplementation);
        setProp(props, SslConfigs.SSL_TRUSTMANAGER_ALGORITHM_CONFIG, sslTrustmanagerAlgorithm);
        setProp(props, SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, sslTruststoreType);
    }
    private void setSslEndpointIdentificationAlgorithm(Map<String, Object> props) {

        if (Objects.nonNull(sslEendpointIdentificationAlgorithm)) {
            props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, sslEendpointIdentificationAlgorithm);
        }
    }
    private void setProp(Map<String, Object> props, String key, String value) {
        if (!StringUtils.isEmpty(value)) {
            props.put(key, value);
        }
    }
}
