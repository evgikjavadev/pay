package ru.vtb.msa.rfrm.integration.arbitration.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import ru.vtb.msa.rfrm.integration.arbitration.config.ArbitrationProperties;
import ru.vtb.msa.rfrm.integration.arbitration.dto.request.ArbitrationRequest;
import ru.vtb.msa.rfrm.integration.arbitration.dto.response.ArbitrationResponse;
import ru.vtb.msa.rfrm.integration.util.client.WebClientBase;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.function.Function;

import static ru.vtb.msa.rfrm.integration.util.enums.ClientName.CALL_ARBITRATION;


@Slf4j
public class ArbitrationClientImpl extends WebClientBase implements ArbitrationClient {
    private final ArbitrationProperties properties;
    public ArbitrationClientImpl(WebClient webClient, ArbitrationProperties properties) {
        super(properties.getRetry().getCount(), properties.getRetry().getDuration(), properties.getHeaders(), webClient);
        this.properties = properties;
    }

    /**
     * Вызов управлением Арбитражем 1906.
     */
    @SneakyThrows
    @Override
    public ArbitrationResponse call(ArbitrationRequest request) {
        log.info("Старт вызова {}", CALL_ARBITRATION.getValue());
        ArbitrationResponse response = this.post(getUrl(), request, ArbitrationResponse.class);
        log.info("Финиш вызова {}", CALL_ARBITRATION.getValue());
        return response;
    }

    @NotNull
    private Function<UriBuilder, URI> getUrl() {
        return uriBuilder -> uriBuilder
                .path(properties.getCheck()).build();
    }
}