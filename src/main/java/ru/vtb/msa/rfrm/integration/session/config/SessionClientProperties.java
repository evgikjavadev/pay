package ru.vtb.msa.rfrm.integration.session.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.vtb.msa.rfrm.integration.util.client.RetryProperties;

import java.util.List;

@Data
@ConfigurationProperties("session-client")
public class SessionClientProperties {
    /**
     * Базовый путь подключения к СС Сессионные данные.
     */
    private String baseUrl;
    /**
     * Идентификатор регистрации клиента для которой будет получаться ТУЗ.
     */
    private String queryPath;
    private String clientContainer;
    private String employeeContainer;
    private List<String> roleList;
    private RetryProperties retry;
}