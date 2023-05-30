package ru.vtb.msa.rfrm.integration.arbitration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.MultiValueMap;
import ru.vtb.msa.rfrm.integration.util.client.RetryProperties;


@Data
@ConfigurationProperties("call-arbitration")
public class ArbitrationProperties {
    private String url;
    private String check;
    private MultiValueMap<String, String> headers;
    private RetryProperties retry;
}