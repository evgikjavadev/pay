package ru.vtb.msa.rfrm.integration.util.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;
import reactor.util.retry.Retry;
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.CommonResponseAccounts;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Response;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public abstract class WebClientBase {
    private static final String KEY_NOT_AUTHORIZED = "tyk.io";
    private static final String X_GENERATOR = "X-Generator";
    private final int maxAttempts;
    private final int duration;
    private final WebClient webClient;

    public <T, R extends CommonResponseAccounts<Object>> Response<?> post(Long mdmIdFromKafka, Function<UriBuilder, URI> function, T request, Class<R> clazz) {

        try {
            ResponseEntity<R> response = webClient.post()
                    .uri(function)
                    .body(BodyInserters.fromValue(request))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header("X-Mdm-Id", String.valueOf(mdmIdFromKafka))
                    .accept(MediaType.ALL)
                    .retrieve()
                    .toEntity(clazz)
                    .retryWhen(Retry.fixedDelay(maxAttempts, Duration.ofMillis(duration))
                            .filter(WebClientBase::isRequestTimeout))
                    .block();
            assert response != null;
            return Response.builder()
                    .headers(response.getHeaders())
                    .body(response.getBody())
                    .build();

        } catch (WebClientResponseException we) {
            log.error(we.getMessage());
            throw new HttpStatusException(we.getMessage(), we.getResponseBodyAsString(), we.getStatusCode());

        } catch (IllegalStateException exception) {
            log.error(exception.getMessage(), exception.fillInStackTrace());
            throw new HttpStatusException(exception.getMessage(), "",
                    ((WebClientResponseException) exception.getCause()).getStatusCode());
        }
    }

    public static boolean isRequestTimeout(Throwable throwable) {
        return throwable instanceof WebClientResponseException &&
                (((WebClientResponseException) throwable).getStatusCode().equals(HttpStatus.REQUEST_TIMEOUT) ||
                        ((WebClientResponseException) throwable).getStatusCode().equals(HttpStatus.UNAUTHORIZED) ||
                        isForbiddenKeNotAuthorized(throwable));
    }

    public static boolean isForbiddenKeNotAuthorized(Throwable throwable) {
        return ((WebClientResponseException) throwable).getStatusCode().equals(HttpStatus.FORBIDDEN) &&
                ((WebClientResponseException) throwable).getHeaders().get(X_GENERATOR).stream()
                        .findFirst().orElse("").equals(KEY_NOT_AUTHORIZED);
    }
}