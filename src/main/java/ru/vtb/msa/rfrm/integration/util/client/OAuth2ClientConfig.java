package ru.vtb.msa.rfrm.integration.util.client;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.endpoint.WebClientReactiveClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import ru.vtb.msa.mdclogging.client.webclient.MdcLoggingWebClientCustomizer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.POST;


@Slf4j
@Configuration
@RequiredArgsConstructor
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@EnableConfigurationProperties({OAuth2ClientProperties.class})
public class OAuth2ClientConfig {
    public static final String EPA_CLIENT = "epa-tyk-client";
    private static final String EPA_AUTHORIZED_FILTER = "epaAuthorizedFilter";
    private final OAuth2ClientProperties oAuth2ClientProperties;

    private final ConnectionProvider connProvider = ConnectionProvider
            .builder("webclient-conn-pool")
            .maxConnections(80)
            .maxIdleTime(Duration.ofMillis(10))
            .maxLifeTime(Duration.ofMillis(10000))
            .pendingAcquireMaxCount(10)
            .pendingAcquireTimeout(Duration.ofMillis(40000))
            .build();


    @Bean(EPA_AUTHORIZED_FILTER)
    public ServerOAuth2AuthorizedClientExchangeFilterFunction getEPA_AUTHORIZED_FILTER() {
        MdcLoggingWebClientCustomizer customizer = new MdcLoggingWebClientCustomizer();
        ClientHttpConnector httpConnector = this.getClientHttp().getClientHttp(EPA_AUTHORIZED_FILTER);

        WebClient.Builder builder = WebClient.builder()
                 .filter(new RequestLoggingFilterFunction(EPA_AUTHORIZED_FILTER))
                .clientConnector(httpConnector);
        customizer.customize(builder);
        return this.authorizedClientFilter(builder.build(), EPA_CLIENT);
    }

    private ServerOAuth2AuthorizedClientExchangeFilterFunction authorizedClientFilter(WebClient webClient, String client) {

        ReactiveClientRegistrationRepository clientRegistrationRepository = this.reactiveClientRegistrationRepository();
        WebClientReactiveClientCredentialsTokenResponseClient accessTokenResponseClient = new WebClientReactiveClientCredentialsTokenResponseClient();
        accessTokenResponseClient.setWebClient(webClient);

        ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider = ReactiveOAuth2AuthorizedClientProviderBuilder
                .builder()
                .clientCredentials(c -> {
                    c.accessTokenResponseClient(accessTokenResponseClient);
                })
                .build();

        InMemoryReactiveOAuth2AuthorizedClientService authorizedClientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrationRepository);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2FilterFunction = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth2FilterFunction.setDefaultOAuth2AuthorizedClient(true);
        oauth2FilterFunction.setDefaultClientRegistrationId(client);
        oauth2FilterFunction.setAuthorizationFailureHandler((authorizationException, principal, attributes) ->
                authorizedClientService.removeAuthorizedClient(((ClientAuthorizationException) authorizationException).getClientRegistrationId(), principal.getName()));
        return oauth2FilterFunction;
    }

    private ReactiveClientRegistrationRepository reactiveClientRegistrationRepository() {
        List<ClientRegistration> clientRegistrations = new ArrayList<>();

        oAuth2ClientProperties.getRegistration()
                .forEach((k, v) -> {
                    String tokenUri = oAuth2ClientProperties.getProvider().get(k).getTokenUri();
                    ClientRegistration clientRegistration = ClientRegistration
                            .withRegistrationId(k)
                            .tokenUri(tokenUri)
                            .clientId(v.getClientId())
                            .clientSecret(v.getClientSecret())
                            .clientAuthenticationMethod(POST)
                            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                            .build();
                    clientRegistrations.add(clientRegistration);
                });

        return new InMemoryReactiveClientRegistrationRepository(clientRegistrations);
    }

    @Bean
    ClientHttp getClientHttp() {
        return new ClientHttp();
    }

    public class ClientHttp {
        @SneakyThrows
        public ClientHttpConnector getClientHttp(String nameLogClass) {
            SslContext sslContext = SslContextBuilder
                    .forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();

            return new ReactorClientHttpConnector(HttpClient
                    .create()
                    .create(connProvider)
                    .secure(t -> t.sslContext(sslContext))
                    .resolver(DefaultAddressResolverGroup.INSTANCE));
        }
    }
}
