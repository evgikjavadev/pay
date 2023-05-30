package ru.vtb.msa.rfrm.integration.checking.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import ru.vtb.msa.rfrm.integration.checking.config.ComplexCheckProperties;
import ru.vtb.msa.rfrm.integration.checking.dto.request.ComplexCheckRequest;
import ru.vtb.msa.rfrm.integration.checking.dto.response.ComplexCheckResponse;
import ru.vtb.msa.rfrm.integration.util.client.WebClientBase;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.function.Function;

import static ru.vtb.msa.rfrm.integration.util.enums.ClientName.COMPLEX_CHECK;

@Slf4j
public class ComplexCheckClientImpl extends WebClientBase implements ComplexCheckClient {
    private final ComplexCheckProperties properties;
    public ComplexCheckClientImpl(WebClient webClient, ComplexCheckProperties properties) {
        super(properties.getRetry().getCount(), properties.getRetry().getDuration(),properties.getHeaders(), webClient);
        this.properties = properties;
    }

    /**
     * Проведение проверки паспорта ФЛ и нахождения его в санкционных списках
     */
    @SneakyThrows
    @Override
    public ComplexCheckResponse check(ComplexCheckRequest request) {
        log.info("Старт вызова {}", COMPLEX_CHECK.getValue());
        ComplexCheckResponse response = this.post(getUrl(), request, ComplexCheckResponse.class);
        log.info("Финиш вызова {}", COMPLEX_CHECK.getValue());
        return response;
    }

    @NotNull
    private Function<UriBuilder, URI> getUrl() {
        return uriBuilder -> uriBuilder
                .path(properties.getCheck()).build();
    }
}