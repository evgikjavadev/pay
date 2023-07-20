package ru.vtb.msa.rfrm.integration.util.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.responsenew.ResponseCommonWebClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    List<String> mdmIdFromHeader = new ArrayList<>();
    public <T, R> R post(Function<UriBuilder, URI> function, AccountInfoRequest request, Class<R> accountsClass) {

        Map<String, Map<String, String>> result;
        try {


            R block = webClient.post()
                    .uri(function)
                    .body(BodyInserters.fromValue(request))
                    .accept(MediaType.ALL)
                    .retrieve()
                    //.bodyToMono(new ParameterizedTypeReference<ResponseCommonWebClient<ResponseCommonWebClient>>() {
                    //})

                    .bodyToMono(accountsClass)

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
