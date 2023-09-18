package ru.vtb.msa.rfrm.integration.rfrmkafka.processing;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.StringUtils;
import ru.vtb.msa.rfrm.integration.rfrmkafka.mapper.QuestionnairesMapper;
import ru.vtb.msa.rfrm.integration.rfrmkafka.service.ProcessQuestionnairesService;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel;


import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
public class KafkaProcessingPlatformConfig {
    private static final String SECURITY_PROTOCOL = "security.protocol";
    @Value("${process.platform.kafka.consumer.group-id}")
    private String groupId;
    @Value("${process.platform.kafka.bootstrap.server}")
    private String servers;
    @Value("${process.platform.kafka.session.timeout.ms:15000}")
    private String sessionTimeout;
    @Value("${dprocess.platform.kafka.consumer.schedule.time:5000}")
    private long consumerScheduleTime;
    @Value("${process.platform.kafka.consumer.pool.time:30000}")
    private long consumerPoolTime;
    @Value("${process.platform.kafka.max.partition.fetch.bytes:1048576}")
    private String maxPartitionFetchBytes;
    @Value("${process.platform.kafka.max.poll.records:500}")
    private String maxPollRecords;
    @Value("${process.platform.kafka.max.poll.interval.ms:300000}")
    private String maxPollIntervalsMs;

    // для организации "SSL":
    @Value("${process.platform.kafka.security.protocol:}")
    private String securityProtocol;

    // При securityProtocol = "SSL" обязательны:
    @Value("${process.platform.kafka.ssl.endpoint.identification.algorithm:}")
    private String sslEendpointIdentificationAlgorithm;
    @Value("${process.platform.kafka.ssl.truststore.location:}")
    private String sslTruststoreLocation;
    @Value("${process.platform.kafka.ssl.truststore.password:}")
    private String sslTruststorePassword;
    @Value("${process.platform.kafka.ssl.keystore.location:}")
    private String sslKeystoreLocation;
    @Value("${process.platform.kafka.ssl.keystore.password:}")
    private String sslKeystorePassword;
    @Value("${process.platform.kafka.ssl.key.password:}")
    private String sslKeyPassword;

    // При securityProtocol = "SSL" необязательны:
    @Value("${process.platform.kafka.ssl.cipher.suites:}")
    private String sslCipherSuites;
    @Value("${process.platform.kafka.ssl.enabled.protocols:}")
    private String sslEnabledProtocols;
    @Value("${process.platform.kafka.ssl.keymanager.algorithm:}")
    private String sslKeymanagerAlgorithm;
    @Value("${process.platform.kafka.ssl.keystore.type:}")
    private String sslKeystoreType;
    @Value("${process.platform.kafka.ssl.protocol:}")
    private String sslProtocol;
    @Value("${process.platform.kafka.ssl.provider:}")
    private String sslProvider;
    @Value("${process.platform.kafka.ssl.secure.random.implementation:}")
    private String sslSecureRandomImplementation;
    @Value("${process.platform.kafka.ssl.trustmanager.algorithm:}")
    private String sslTrustmanagerAlgorithm;
    @Value("${process.platform.kafka.ssl.truststore.type:}")
    private String sslTruststoreType;

    @Bean
    public ConsumerFactory<String, QuestionnairesKafkaModel> consumerFactory(KafkaProperties kafkaProp) {
        Map<String, Object> props = kafkaProp.buildConsumerProperties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "ru.vtb.msa.rfrm.integration.rfrmkafka.model.QuestionnairesKafkaModel");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalsMs);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, Boolean.FALSE);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        setSecurityProps(props);

        DefaultKafkaConsumerFactory factory = new DefaultKafkaConsumerFactory<String, QuestionnairesKafkaModel>(props);
        factory.setKeyDeserializer(new StringDeserializer());
        factory.setValueDeserializer(new JsonDeserializer());

        return factory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, QuestionnairesKafkaModel> kafkaListenerContainerFactory(ConsumerFactory<String, QuestionnairesKafkaModel> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, QuestionnairesKafkaModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.setConcurrency(1);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        //factory.getContainerProperties().setAsyncAcks(true);
        factory.getContainerProperties().setPollTimeout(5000);
        factory.getContainerProperties().setMicrometerEnabled(true);
       // factory.getContainerProperties().setMicrometerTags();

        return factory;
    }

    @Bean
    KafkaProcessingPlatformClient client(ProcessQuestionnairesService service) {
        QuestionnairesMapper mapper = Mappers.getMapper(QuestionnairesMapper.class);
        return new KafkaProcessingPlatformClient(service, mapper);
    }

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