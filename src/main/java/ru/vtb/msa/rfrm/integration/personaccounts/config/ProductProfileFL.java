package ru.vtb.msa.rfrm.integration.personaccounts.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.MultiValueMap;
import ru.vtb.msa.rfrm.integration.util.client.RetryProperties;


@Data
@ConfigurationProperties("product-profile-fl-accounts")
public class ProductProfileFL {
    private String url;
    private String resource;
    private MultiValueMap<String, String> headers;
    private RetryProperties retry;
}