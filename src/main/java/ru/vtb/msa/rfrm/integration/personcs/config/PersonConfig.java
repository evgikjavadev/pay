package ru.vtb.msa.rfrm.integration.personcs.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.mdclogging.client.webclient.MdcLoggingWebClientCustomizer;
import ru.vtb.msa.rfrm.integration.personcs.client.PersonClient;
import ru.vtb.msa.rfrm.integration.personcs.client.PersonClientImpl;
import ru.vtb.msa.rfrm.integration.util.client.OAuth2ClientConfig;
import ru.vtb.msa.rfrm.integration.util.client.RequestLoggingFilterFunction;

@Configuration
@EnableConfigurationProperties(ProductProfileFL.class)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RequiredArgsConstructor
public class PersonConfig {

    @Bean
    public PersonClient personClient(MdcLoggingWebClientCustomizer customizer,
                                     ProductProfileFL properties,
                                     @Qualifier("epaAuthorizedFilter") ServerOAuth2AuthorizedClientExchangeFilterFunction oAuth2Authorized,
                                     OAuth2ClientConfig.ClientHttp clientHttp) {

        customizer = new MdcLoggingWebClientCustomizer();
        WebClient.Builder webClient = WebClient.builder();
        webClient
                .baseUrl(properties.getUrl())
                .filter(oAuth2Authorized)
                .filter(new RequestLoggingFilterFunction(PersonConfig.class.getName()))
                .clientConnector(clientHttp.getClientHttp(PersonConfig.class.getName()));
        customizer.customize(webClient);
        return new PersonClientImpl(webClient.build(), properties);
    }
}
