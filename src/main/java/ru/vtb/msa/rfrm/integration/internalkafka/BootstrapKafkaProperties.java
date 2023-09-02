package ru.vtb.msa.rfrm.integration.internalkafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(value = "mykafka")
public class BootstrapKafkaProperties {
    private String bootstrapAddress;
}
