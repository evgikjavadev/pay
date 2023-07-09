package ru.vtb.msa.rfrm.integration.personcs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.MultiValueMap;
import ru.vtb.msa.rfrm.integration.util.client.RetryProperties;


@Data
@ConfigurationProperties("product-profile-fl")
public class ProductProfileFL {
    private String url;
    private String search;
//    private String updatePhone;
    private MultiValueMap<String, String> headers;
    private RetryProperties retry;
}