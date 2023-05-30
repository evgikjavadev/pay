package ru.vtb.msa.rfrm.integration.employee.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import ru.vtb.msa.rfrm.integration.employee.config.EmployeeClientProperties;
import ru.vtb.msa.rfrm.integration.employee.dto.EmployeeResponse;
import ru.vtb.msa.rfrm.integration.util.client.WebClientBase;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.function.Function;

import static ru.vtb.msa.rfrm.integration.util.enums.ClientName.EMPLOYEE;


@Slf4j
public class EmployeeClientImpl extends WebClientBase implements EmployeeClient {

    private final EmployeeClientProperties properties;

    public EmployeeClientImpl(WebClient webClient, EmployeeClientProperties properties) {
        super(properties.getRetry().getCount(), properties.getRetry().getDuration(),properties.getHeaders(), webClient);
        this.properties = properties;
    }

    @SneakyThrows
    @Override
    public EmployeeResponse getEmployee(String employeeLogin) {
        log.info("Старт вызова {}", EMPLOYEE.getValue());
        EmployeeResponse response = get(getUrl(employeeLogin), employeeLogin, EmployeeResponse.class);
        log.info("Финиш вызова {}", EMPLOYEE.getValue());
        return response;
    }

    @NotNull
    private Function<UriBuilder, URI> getUrl(String employeeLogin) {
        return uriBuilder -> uriBuilder
                .path(properties.getGet())
                .path(employeeLogin)
                .build();
    }
}