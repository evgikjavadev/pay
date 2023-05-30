package ru.vtb.msa.rfrm.integration.util.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties
public class RetryProperties {
    private int count;
    private int duration;
}
