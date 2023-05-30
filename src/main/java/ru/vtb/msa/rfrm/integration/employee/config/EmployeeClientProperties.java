package ru.vtb.msa.rfrm.integration.employee.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.MultiValueMap;
import ru.vtb.msa.rfrm.integration.util.client.RetryProperties;

@Data
@ConfigurationProperties("employee-client")
public class EmployeeClientProperties {
    private String url;
    private String get;
    private MultiValueMap<String, String> headers;
    private RetryProperties retry;
}