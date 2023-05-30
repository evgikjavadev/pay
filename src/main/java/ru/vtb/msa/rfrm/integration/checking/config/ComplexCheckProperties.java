package ru.vtb.msa.rfrm.integration.checking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.MultiValueMap;
import ru.vtb.msa.rfrm.integration.util.client.RetryProperties;


@Data
@ConfigurationProperties("complex-check")
public class ComplexCheckProperties {
    private String url;
    private String check;
    private MultiValueMap<String, String> headers;
    private RetryProperties retry;
}