package ru.vtb.msa.rfrm.integration.employee.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.mdclogging.client.webclient.MdcLoggingWebClientCustomizer;
import ru.vtb.msa.rfrm.integration.employee.client.EmployeeClient;
import ru.vtb.msa.rfrm.integration.employee.client.EmployeeClientImpl;
import ru.vtb.msa.rfrm.integration.util.client.OAuth2ClientConfig;
import ru.vtb.msa.rfrm.integration.util.client.RequestLoggingFilterFunction;

@Configuration
@EnableConfigurationProperties({EmployeeClientProperties.class})
public class EmployeeClientConfig {

    @Bean("employeeClient")
    EmployeeClient employeeClient(MdcLoggingWebClientCustomizer customizer, EmployeeClientProperties properties,
                                  @Qualifier("epaAuthorizedFilter") ServerOAuth2AuthorizedClientExchangeFilterFunction oAuth2Authorized,
                                  OAuth2ClientConfig.ClientHttp clientHttp) {

        customizer = new MdcLoggingWebClientCustomizer();
        WebClient.Builder webClient = WebClient.builder();
        webClient
                .baseUrl(properties.getUrl())
                .filter(oAuth2Authorized)
                .filter(new RequestLoggingFilterFunction(EmployeeClient.class.getName()))
                .clientConnector(clientHttp.getClientHttp(EmployeeClient.class.getName()));
        customizer.customize(webClient);
        return new EmployeeClientImpl(webClient.build(), properties);
    }
}
