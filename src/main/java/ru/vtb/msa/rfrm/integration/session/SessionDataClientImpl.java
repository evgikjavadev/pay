package ru.vtb.msa.rfrm.integration.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.session.config.SessionClientProperties;
import ru.vtb.msa.rfrm.integration.util.client.WebClientBase;

import java.time.Duration;
import java.util.Map;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static ru.vtb.msa.rfrm.integration.util.enums.ClientName.SESSION_DATA;

@Slf4j
@RequiredArgsConstructor
public class SessionDataClientImpl implements SessionClient {

    private final WebClient webClient;

    private final SessionClientProperties properties;
    private final int maxAttempts;
    private final int duration;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public static final String DELIMITER = "/";

    @Override
    public Map<String, Map<String, String>> getSessionMap(String sessionId) {
        Map<String, Map<String, String>> result;
        String url = builderUrl(sessionId).toString();
        try {
            log.info("Старт вызова {}", SESSION_DATA.getValue());
            result = webClient.get()
                    .uri(u -> u.path(url).build())
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<String>() {
                    })
                    .map(jsonToMapConverterFunction(""))
                    .doOnError(clientResponse -> log.warn("{}: sessionId={}, body={}",
                            "Ошибка при получении данных из СС Сессионные данные",
                            url, clientResponse))
                    .retryWhen(Retry.fixedDelay(maxAttempts, Duration.ofMillis(duration))
                            .filter(WebClientBase::isRequestTimeout))
                    .block();
        } catch (WebClientResponseException ex) {
            log.error(ex.getMessage(), ex.fillInStackTrace());
            throw new HttpStatusException(ex.getMessage(), ex.getResponseBodyAsString(), ex.getStatusCode());
        } catch (IllegalStateException exception) {
            log.error(exception.getMessage(), exception.fillInStackTrace());
            throw new HttpStatusException(exception.getMessage(), "", HttpStatus.REQUEST_TIMEOUT);
        }
        log.info("Финиш вызова {}", SESSION_DATA.getValue());
        return result;
    }

    private StringBuilder builderUrl(String sessionId) {
        return new StringBuilder()
                .append(properties.getQueryPath())
                .append(DELIMITER).append(sessionId);
    }

    private Function<String, Map<String, Map<String, String>>> jsonToMapConverterFunction(String defaultKey) {
        return json -> {
            try {
                return objectMapper.readValue(json, new TypeReference<Map<String, Map<String, String>>>() {
                });
            } catch (JsonProcessingException e) {
                return Map.of(defaultKey, Map.of(defaultKey, json));
            }
        };
    }
}
