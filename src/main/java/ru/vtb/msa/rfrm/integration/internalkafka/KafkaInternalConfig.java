package ru.vtb.msa.rfrm.integration.internalkafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.vtb.msa.rfrm.integration.internalkafka.model.InternalMessageModel;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Slf4j
@Component
public class KafkaInternalConfig {
    private static final String SECURITY_PROTOCOL = "security.protocol";
    public static final int COUNT_THREAD = 1;

    @Value("${function.kafka.consumer.group-id}")
    private String groupId;
    @Value("${function.kafka.session.timeout.ms:15000}")
    private String sessionTimeout;
    @Value("${function.kafka.bootstrap.server}")
    private String servers;
    @Value("${function.kafka.max.partition.fetch.bytes:1048576}")
    private String maxPartitionFetchBytes;
    @Value("${function.kafka.max.poll.records:500}")
    private String maxPollRecords;
    @Value("${function.kafka.max.poll.interval.ms:300000}")
    private String maxPollIntervalsMs;
    @Value("${function.kafka.pause.work.consumer.ms:0}")
    private String pauseWorkConsumerMs;


    // для организации "SSL":
    @Value("${function.kafka.security.protocol:SSL}")
    private String securityProtocol;

    // При securityProtocol = "SSL" обязательны:
    @Value("${function.kafka.ssl.endpoint.identification.algorithm:https}")
    private String sslEendpointIdentificationAlgorithm;
    @Value("${function.kafka.ssl.truststore.location:}")
    private String sslTruststoreLocation;
    @Value("${function.kafka.ssl.truststore.password:}")
    private String sslTruststorePassword;
    @Value("${function.kafka.ssl.keystore.location:}")
    private String sslKeystoreLocation;
    @Value("${function.kafka.ssl.keystore.password:}")
    private String sslKeystorePassword;
    @Value("${function.kafka.ssl.key.password:}")
    private String sslKeyPassword;

    // При securityProtocol = "SSL" необязательны:
    @Value("${function.kafka.ssl.cipher.suites:}")
    private String sslCipherSuites;
    @Value("${function.kafka.ssl.enabled.protocols:}")
    private String sslEnabledProtocols;
    @Value("${function.kafka.ssl.keymanager.algorithm:}")
    private String sslKeymanagerAlgorithm;
    @Value("${function.kafka.ssl.keystore.type:}")
    private String sslKeystoreType;
    @Value("${function.kafka.ssl.protocol:}")
    private String sslProtocol;
    @Value("${function.kafka.ssl.provider:}")
    private String sslProvider;
    @Value("${function.kafka.ssl.secure.random.implementation:}")
    private String sslSecureRandomImplementation;
    @Value("${function.kafka.ssl.trustmanager.algorithm:}")
    private String sslTrustmanagerAlgorithm;
    @Value("${function.kafka.ssl.truststore.type:}")
    private String sslTruststoreType;

    @Bean
    public ProducerFactory<String, InternalMessageModel> producerInternalFactory(KafkaProperties kafkaProp) {
        Map<String, Object> props = kafkaProp.buildProducerProperties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "ru.vtb.msa.rfrm.integration.internalkafka.model.InternalMessageModel");
        setSecurityProps(props);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean("kafkaTemplateInternal")
    public KafkaTemplate<String, InternalMessageModel> kafkaTemplate(@Qualifier("producerInternalFactory") ProducerFactory<String, InternalMessageModel> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaInternalProducer produceInternal(@Qualifier("kafkaTemplateInternal") KafkaTemplate<String, InternalMessageModel> kafkaTemplate) {
        return new KafkaInternalProducer(kafkaTemplate);
    }

    private void setSecurityProps(Map<String, Object> props) {
        setProp(props, SECURITY_PROTOCOL, securityProtocol);

        // При securityProtocol = "SSL" обязательны:
        setSslEendpointIdentificationAlgorithm(props);
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

    private void setProp(Map<String, Object> props, String key, String value) {
        if (!StringUtils.isEmpty(value)) {
            props.put(key, value);
        }
    }

    private void setSslEendpointIdentificationAlgorithm(Map<String, Object> props) {
        if (Objects.nonNull(sslEendpointIdentificationAlgorithm)) {
            props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, sslEendpointIdentificationAlgorithm);
        }
    }

}
