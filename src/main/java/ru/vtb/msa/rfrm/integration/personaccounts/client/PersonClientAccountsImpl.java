package ru.vtb.msa.rfrm.integration.personaccounts.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.responsenew.ResponseCommonWebClient;
import ru.vtb.msa.rfrm.integration.personaccounts.config.ProductProfileFL;
import ru.vtb.msa.rfrm.integration.util.client.WebClientBase;

import static ru.vtb.msa.rfrm.integration.util.enums.ClientName.PRODUCT_PROFILE_FL;

@Slf4j
public class PersonClientAccountsImpl extends WebClientBase implements PersonClientAccounts {
    private final ProductProfileFL properties;

    public PersonClientAccountsImpl(WebClient webClient, ProductProfileFL properties) {
        super(properties.getRetry().getCount(), properties.getRetry().getDuration(), properties.getHeaders(), webClient);
        this.properties = properties;
    }

    @Override
    public ResponseCommonWebClient getPersonAccounts(AccountInfoRequest request) {
        log.info("Старт вызова {}", PRODUCT_PROFILE_FL.getValue());

        ResponseCommonWebClient responseCommonWebClient = this.post(uriBuilder -> uriBuilder.path(properties.getResource()).build(),
                request, ResponseCommonWebClient.class);

        log.info("Финиш вызова {}", PRODUCT_PROFILE_FL.getValue());
        return responseCommonWebClient;
    }

//    @Override
//    public String getHeadersPersonAccount(AccountInfoRequest request) {
//        String headerMdmId = this.getHeaderMdmId(uriBuilder -> uriBuilder.path(properties.getResource()).build(),
//                request, String.class);
//        return headerMdmId;
//    }

}
