package ru.vtb.msa.rfrm.integration.util.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;
import reactor.util.retry.Retry;
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.repository.PaymentTaskRepository;
import ru.vtb.msa.rfrm.repository.TaskStatusHistoryRepository;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public abstract class WebClientBase {
    private static final String KEY_NOT_AUTHORIZED = "tyk.io";
    private static final String X_GENERATOR = "X-Generator";
    private final int maxAttempts;
    private final int duration;
    private final MultiValueMap<String, String> headers;
    private final WebClient webClient;

    //private final TaskStatusHistoryRepository repository;

    public <T, R> R post(Function<UriBuilder, URI> function, T request, Class<R> clazz) {
        try {
            return webClient.post()
                    .uri(function)
                    .body(BodyInserters.fromValue(request))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .headers(getHttpHeaders(headers))
                    .accept(MediaType.ALL)
                    .retrieve()
                    .bodyToMono(clazz)
                    .retryWhen(Retry.fixedDelay(maxAttempts, Duration.ofMillis(duration))
                            .filter(WebClientBase::isRequestTimeout))
                    .block();
        } catch (WebClientResponseException we) {
            log.error(we.getMessage());

            if (we.getStatusCode().equals(HttpStatus.NOT_FOUND)) {

                //repository.save()
            }



            throw new HttpStatusException(we.getMessage(), we.getResponseBodyAsString(), we.getStatusCode());
        } catch (IllegalStateException exception) {
            log.error(exception.getMessage(), exception.fillInStackTrace());

            throw new HttpStatusException(exception.getMessage(), "",
                    ((WebClientResponseException) exception.getCause()).getStatusCode());
        }
    }



    private Consumer<HttpHeaders> getHttpHeaders(MultiValueMap<String, String> headers) {
        return httpHeaders -> httpHeaders.addAll(Optional.ofNullable(headers)
                .orElse(new HttpHeaders()));
    }

    public static boolean isRequestTimeout(Throwable throwable) {
        return throwable instanceof WebClientResponseException &&
                ((WebClientResponseException) throwable).getStatusCode().equals(HttpStatus.REQUEST_TIMEOUT) ||
                ((WebClientResponseException) throwable).getStatusCode().equals(HttpStatus.UNAUTHORIZED) ||
                isForbiddenKeNotAuthorized(throwable);
    }

    public static boolean isForbiddenKeNotAuthorized(Throwable throwable) {
        return ((WebClientResponseException) throwable).getStatusCode().equals(HttpStatus.FORBIDDEN) &&
                ((WebClientResponseException) throwable).getHeaders().get(X_GENERATOR).stream()
                        .findFirst().orElse("").equals(KEY_NOT_AUTHORIZED);
    }
}
