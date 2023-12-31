package ru.vtb.msa.rfrm.integration.util.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpRequestDecorator;
import org.springframework.lang.NonNull;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static java.lang.Math.min;
import static java.util.UUID.randomUUID;


@Slf4j
@RequiredArgsConstructor
public class RequestLoggingFilterFunction implements ExchangeFilterFunction {

    private final String className;

    @Override
    @NonNull
    public Mono<ClientResponse> filter(@NonNull ClientRequest request, @NonNull ExchangeFunction next) {
        if (!log.isDebugEnabled()) {
            return next.exchange(request);
        }

        String clientRequestId = randomUUID().toString();

        AtomicBoolean requestLogged = new AtomicBoolean(false);
        AtomicBoolean responseLogged = new AtomicBoolean(false);

        StringBuilder capturedRequestBody = new StringBuilder();
        StringBuilder capturedResponseBody = new StringBuilder();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        return next.exchange(ClientRequest.from(request).body(new BodyInserter<>() {

                    @Override
                    @NonNull
                    public Mono<Void> insert(@NonNull ClientHttpRequest req, @NonNull Context context) {
                        return request.body().insert(new ClientHttpRequestDecorator(req) {

                            @Override
                            @NonNull
                            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                                return super.writeWith(Flux.from(body).doOnNext(data -> capturedRequestBody.append(body))); // number of bytes appended is maxed in real code
                            }

                        }, context);
                    }
                }).build())
                .doOnNext(response -> requestLog(requestLogged, request, capturedRequestBody))
                .doOnError(error -> requestErrorLog(requestLogged, className, clientRequestId, request, error))
                .map(response -> response.mutate().body(transformer -> transformer
                                .doOnNext(body -> capturedResponseBody.append(body)) // number of bytes appended is maxed in real code
                                .doOnTerminate(() -> {
                                    if (stopWatch.isRunning()) {
                                        stopWatch.stop();
                                    }
                                })
                                .doOnComplete(() -> {
                                    responseLog(responseLogged, className, response, stopWatch, capturedResponseBody);
                                })
                                .doOnError(error -> responseErrorLog(responseLogged, className, clientRequestId, stopWatch, error))
                        ).build()
                );
    }

    private static void requestLog(AtomicBoolean requestLogged,
                                   ClientRequest request, StringBuilder capturedRequestBody) {
        if (!requestLogged.getAndSet(true)) {
            /*log.info(new StringBuilder()
                    .append("\n>> Method: ").append(request.method())
                    .append("\n>> URI : ").append(request.url())
                    .append("\n>> Headers : ").append(getHeaders(request)).toString());
            log.debug(new StringBuilder()
                    .append("\n>> Body : ").append(capturedRequestBody).toString());*/
        }
    }

    private static void responseLog(AtomicBoolean responseLogged, String className,
                                    ClientResponse response, StopWatch stopWatch, StringBuilder capturedResponseBody) {
        if (!responseLogged.getAndSet(true)) {
    /*        log.info(new StringBuilder()
                    .append("\n<< TimeInMillis: ").append(stopWatch.getTotalTimeMillis()).append("ms")
                    .append("\n<< Status: ").append(response.statusCode().value())
                    .append("\n<< Headers: ").append(getHeaders(response)).toString());*/
            log.debug(new StringBuilder()
                    .append("\n<< Body : ").append(capturedResponseBody).toString());
        }
    }

    private static void responseErrorLog(AtomicBoolean responseLogged, String className, String clientRequestId,
                                         StopWatch stopWatch, Throwable error) {
        if (!responseLogged.getAndSet(true)) {
            log.debug(new StringBuilder("|\n<---<< Error parsing RESPONSE for outgoing")
                    .append("\n<< className : ").append(className)
                    .append("\n<< clientRequestId : ").append(clientRequestId)
                    .append("\n<< clientRequestExecutionTimeInMillis : ").append(stopWatch.getTotalTimeMillis()).append("ms")
                    .append("\n<< clientErrorMessage : ").append(error.getMessage()).toString()
            );
        }
    }

    private static void requestErrorLog(AtomicBoolean requestLogged, String className, String clientRequestId,
                                        ClientRequest request, Throwable error) {
        if (!requestLogged.getAndSet(true)) {
            log.debug(new StringBuilder()
                    .append("\n>> className : ").append(className)
                    .append("\n>> clientRequestId : ").append(clientRequestId)
                    .append("\n>> clientRequestMethod : ").append(clientRequestId)
                    .append("\n>> clientRequestUrl : ").append(request.url())
                    .append("\n>> clientRequestHeaders : ").append(getHeaders(request))
                    .append("\n>> Error : ").append(error.getMessage()).toString()
            );
        }
    }

    @NotNull
    private static String getHeaders(ClientRequest request) {
        return request.headers().keySet()
                .stream()
                .map(key -> key + "=" + request.headers().get(key))
                .collect(Collectors.joining(", "));
    }

    @NotNull
    private static String getHeaders(ClientResponse request) {

        return request.headers().asHttpHeaders().keySet()
                .stream()
                .map(key -> key + "=" + request.headers().asHttpHeaders().get(key))
                .collect(Collectors.joining(", "));
    }
}
