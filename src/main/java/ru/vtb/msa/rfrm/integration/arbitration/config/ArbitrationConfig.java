package ru.vtb.msa.rfrm.integration.arbitration.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.mdclogging.client.webclient.MdcLoggingWebClientCustomizer;
import ru.vtb.msa.rfrm.integration.arbitration.client.ArbitrationClient;
import ru.vtb.msa.rfrm.integration.arbitration.client.ArbitrationClientImpl;
import ru.vtb.msa.rfrm.integration.util.client.OAuth2ClientConfig;
import ru.vtb.msa.rfrm.integration.util.client.RequestLoggingFilterFunction;

@Configuration
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@EnableConfigurationProperties({ArbitrationProperties.class})
public class ArbitrationConfig {

    @Bean("arbitrationClient")
    public ArbitrationClientImpl arbitrationClient(MdcLoggingWebClientCustomizer customizer,
                                                   ArbitrationProperties properties,
                                                   @Qualifier("epaAuthorizedFilter") ServerOAuth2AuthorizedClientExchangeFilterFunction oAuth2Authorized,
                                                   OAuth2ClientConfig.ClientHttp clientHttp) {
        WebClient.Builder webClient = WebClient.builder();
        webClient
                .baseUrl(properties.getUrl())
                .filter(oAuth2Authorized)
                .filter(new RequestLoggingFilterFunction(ArbitrationClient.class.getName()))
                .clientConnector(clientHttp.getClientHttp(ArbitrationClient.class.getName()));
        customizer.customize(webClient);

        return new ArbitrationClientImpl(webClient.build(), properties);
    }
}
