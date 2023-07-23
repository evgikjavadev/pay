package ru.vtb.msa.rfrm.integration.util.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.*;
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
    List<String> mdmIdFromHeader = new ArrayList<>();
    Map<String, Map<String, String>> result;

    public Object post(Function<UriBuilder, URI> function, AccountInfoRequest request, Class<String> stringClass) {

        try {

            Map<StringBuilder, Map<StringBuilder, StringBuilder>> block = webClient.post()
                    .uri(function)
                    .body(BodyInserters.fromValue(request))
                    .accept(MediaType.ALL)
                    .retrieve()
//                    .bodyToMono(new ParameterizedTypeReference<String>() {
//                    })

                    .bodyToMono(String.class)

                    //.bodyToMono(ResponseCommonWebClient.class)
                    .map(jsonString -> {
                        //Mono<String> just = Mono.just(jsonString);
                        ObjectMapper objectMapper = new ObjectMapper();
                        TypeReference<Map<StringBuilder, Map<StringBuilder, StringBuilder>>> typeReference = new TypeReference<>() {
                        };

                        try {
                            return objectMapper.readValue(jsonString, typeReference);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                    })
                    .retryWhen(Retry.fixedDelay(maxAttempts, Duration.ofMillis(duration))
                            .filter(this::isRequestTimeout))
                    .block();


            System.out.println("my6 header = " + mdmIdFromHeader);
            System.out.println("my7 response = " + block);

            return block;

        } catch (WebClientResponseException we) {
            log.error(we.getMessage());
            throw new HttpStatusException(we.getMessage(), we.getResponseBodyAsString(), we.getStatusCode());
        } catch (IllegalStateException exception) {
            log.error(exception.getMessage(), exception.fillInStackTrace());
            throw new HttpStatusException(exception.getMessage(), "",
                    ((WebClientResponseException) exception.getCause()).getStatusCode());
        }
    }

    public Mono<Map<String, Map<String, String>>> parseJson(Mono<String> jsonStringMono) {
        return jsonStringMono.map(jsonString -> {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, Map<String, String>>> typeReference = new TypeReference<>() {};
            try {
                return objectMapper.readValue(jsonString, typeReference);
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse JSON", e);
            }
        });
    }


    private Consumer<HttpHeaders> getHttpHeaders(MultiValueMap<String, String> headers) {
        return httpHeaders -> httpHeaders.addAll(Optional.ofNullable(headers)
                .orElse(new HttpHeaders()));
    }

    public boolean isRequestTimeout(Throwable throwable) {
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
