package ru.vtb.msa.rfrm.integration.checking.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.mdclogging.client.webclient.MdcLoggingWebClientCustomizer;
import ru.vtb.msa.rfrm.integration.checking.client.ComplexCheckClient;
import ru.vtb.msa.rfrm.integration.checking.client.ComplexCheckClientImpl;
import ru.vtb.msa.rfrm.integration.util.client.OAuth2ClientConfig;
import ru.vtb.msa.rfrm.integration.util.client.RequestLoggingFilterFunction;

@Configuration
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@EnableConfigurationProperties({ComplexCheckProperties.class})
public class ComplexCheckConfig {

    @Bean(name = "checkingClient")
    public ComplexCheckClient clientDkoClient(MdcLoggingWebClientCustomizer customizer, ComplexCheckProperties properties,
                                              @Qualifier("epaAuthorizedFilter") ServerOAuth2AuthorizedClientExchangeFilterFunction oAuth2Authorized,
                                              OAuth2ClientConfig.ClientHttp clientHttp) {
        customizer = new MdcLoggingWebClientCustomizer();
        WebClient.Builder webClient = WebClient.builder();
        webClient
                .baseUrl(properties.getUrl())
                .filter(oAuth2Authorized)
                .filter(new RequestLoggingFilterFunction(ComplexCheckClient.class.getName()))
                .clientConnector(clientHttp.getClientHttp(ComplexCheckClient.class.getName()));
        customizer.customize(webClient);

        return new ComplexCheckClientImpl(webClient.build() ,properties);
    }
}
