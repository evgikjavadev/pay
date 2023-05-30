package ru.vtb.msa.rfrm.integration.session.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.mdclogging.client.webclient.MdcLoggingWebClientCustomizer;
import ru.vtb.msa.rfrm.integration.session.SessionClient;
import ru.vtb.msa.rfrm.integration.session.SessionDataClientImpl;
import ru.vtb.msa.rfrm.integration.util.client.OAuth2ClientConfig;
import ru.vtb.msa.rfrm.integration.util.client.RequestLoggingFilterFunction;

@Slf4j
@Configuration
@RequiredArgsConstructor
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@EnableConfigurationProperties({SessionClientProperties.class})
public class SessionClientConfig {

    private final SessionClientProperties properties;

    @Bean
    SessionClient restSessionClient(@Qualifier("sessionCCAuthorizedFilter") ServerOAuth2AuthorizedClientExchangeFilterFunction oAuth2Authorized,
                                    OAuth2ClientConfig.ClientHttp clientHttp,
                                    MdcLoggingWebClientCustomizer customizer) {
        customizer = new MdcLoggingWebClientCustomizer();
        WebClient.Builder builder = WebClient.builder();
        builder.filter(oAuth2Authorized)
                .filter(new RequestLoggingFilterFunction(SessionClient.class.getName()))
                .clientConnector(clientHttp.getClientHttp(SessionClient.class.getName()))
                .baseUrl(properties.getBaseUrl());

        customizer.customize(builder);
        return new SessionDataClientImpl(builder.build(), properties,3,2000);
    }
}
