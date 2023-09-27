package ru.vtb.msa.rfrm.integration.rfrmkafka.forcore;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreLinkModel;

import java.util.Map;
import java.util.Objects;

@Component
public class KafkaProducerCoreConfig {

    private static final String SECURITY_PROTOCOL = "security.protocol";
    @Value("${coreresult.kafka.topic}")
    private String topic;
    @Value("${coreresult.kafka.bootstrap.server}")
    private String servers;
    @Value("${coreresult.kafka.max.partition.fetch.bytes:1048576}")
    private String maxPartitionFetchBytes;
    @Value("${coreresult.kafka.max.poll.records:500}")
    private String maxPollRecords;
    @Value("${coreresult.kafka.max.poll.interval.ms:300000}")
    private String maxPollIntervalsMs;

    // для организации "SSL":
    @Value("${coreresult.kafka.security.protocol:}")
    private String securityProtocol;

    // При securityProtocol = "SSL" обязательны:
    @Value("${coreresult.kafka.ssl.endpoint.identification.algorithm:}")
    private String sslEendpointIdentificationAlgorithm;
    @Value("${coreresult.kafka.ssl.truststore.location:}")
    private String sslTruststoreLocation;
    @Value("${coreresult.kafka.ssl.truststore.password:}")
    private String sslTruststorePassword;
    @Value("${coreresult.kafka.ssl.keystore.location:}")
    private String sslKeystoreLocation;
    @Value("${coreresult.kafka.ssl.keystore.password:}")
    private String sslKeystorePassword;
    @Value("${coreresult.kafka.ssl.key.password:}")
    private String sslKeyPassword;

    // При securityProtocol = "SSL" необязательны:
    @Value("${coreresult.kafka.ssl.cipher.suites:}")
    private String sslCipherSuites;
    @Value("${coreresult.kafka.ssl.enabled.protocols:}")
    private String sslEnabledProtocols;
    @Value("${coreresult.kafka.ssl.keymanager.algorithm:}")
    private String sslKeymanagerAlgorithm;
    @Value("${coreresult.kafka.ssl.keystore.type:}")
    private String sslKeystoreType;
    @Value("${coreresult.kafka.ssl.protocol:}")
    private String sslProtocol;
    @Value("${coreresult.kafka.ssl.provider:}")
    private String sslProvider;
    @Value("${coreresult.kafka.ssl.secure.random.implementation:}")
    private String sslSecureRandomImplementation;
    @Value("${coreresult.kafka.ssl.trustmanager.algorithm:}")
    private String sslTrustmanagerAlgorithm;
    @Value("${coreresult.kafka.ssl.truststore-type:}")
    private String sslTruststoreType;

    @Bean
    public ProducerFactory<String, PayCoreLinkModel> producerFactory(KafkaProperties kafkaProp) {
        Map<String, Object> props = kafkaProp.buildProducerProperties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        setSecurityProps(props);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaResultRewardProducer producer(@Qualifier("coreresult") KafkaTemplate template) {
        template.setDefaultTopic(topic);
        return new KafkaResultRewardProducer(template);
    }

    @Bean("coreresult")
    public KafkaTemplate<String, PayCoreLinkModel> kafkaTemplate(ProducerFactory<String, PayCoreLinkModel> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    private void setSecurityProps(Map<String, Object> props) {
        checkAndSetProp(props, SECURITY_PROTOCOL, securityProtocol);

        // При securityProtocol = "SSL" обязательны:
        setSslEndpointIdentificationAlgorithm(props);
        checkAndSetProp(props, SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, sslTruststoreLocation);
        checkAndSetProp(props, SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, sslTruststorePassword);
        checkAndSetProp(props, SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, sslKeystoreLocation);
        checkAndSetProp(props, SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, sslKeystorePassword);
        checkAndSetProp(props, SslConfigs.SSL_KEY_PASSWORD_CONFIG, sslKeyPassword);

        // При securityProtocol = "SSL" необязательны:
        checkAndSetProp(props, SslConfigs.SSL_CIPHER_SUITES_CONFIG, sslCipherSuites);
        checkAndSetProp(props, SslConfigs.SSL_ENABLED_PROTOCOLS_CONFIG, sslEnabledProtocols);
        checkAndSetProp(props, SslConfigs.SSL_KEYMANAGER_ALGORITHM_CONFIG, sslKeymanagerAlgorithm);
        checkAndSetProp(props, SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, sslKeystoreType);
        checkAndSetProp(props, SslConfigs.SSL_PROTOCOL_CONFIG, sslProtocol);
        checkAndSetProp(props, SslConfigs.SSL_PROVIDER_CONFIG, sslProvider);
        checkAndSetProp(props, SslConfigs.SSL_SECURE_RANDOM_IMPLEMENTATION_CONFIG, sslSecureRandomImplementation);
        checkAndSetProp(props, SslConfigs.SSL_TRUSTMANAGER_ALGORITHM_CONFIG, sslTrustmanagerAlgorithm);
        checkAndSetProp(props, SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, sslTruststoreType);
    }

    private void checkAndSetProp(Map<String, Object> props, String key, String value) {
        if (!StringUtils.isEmpty(value)) {
            props.put(key, value);
        }
    }

    private void setSslEndpointIdentificationAlgorithm(Map<String, Object> props) {

        if (Objects.nonNull(sslEendpointIdentificationAlgorithm)) {
            props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, sslEendpointIdentificationAlgorithm);
        }
    }

}
