package ru.vtb.msa.rfrm.integration.personaccounts.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.rfrm.integration.personaccounts.SearchMasterAccount;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.ResponseCommon;
import ru.vtb.msa.rfrm.integration.personaccounts.config.ProductProfileFL;
import ru.vtb.msa.rfrm.integration.util.client.WebClientBase;

import static ru.vtb.msa.rfrm.integration.util.client.HeadersConstant.HEADER_NAME_X_MDM_ID;
import static ru.vtb.msa.rfrm.integration.util.enums.ClientName.PRODUCT_PROFILE_FL;

@Slf4j
public class PersonClientAccountsImpl extends WebClientBase implements PersonClientAccounts {
    private final ProductProfileFL properties;

    public PersonClientAccountsImpl(WebClient webClient, ProductProfileFL properties) {
        super(properties.getRetry().getCount(), properties.getRetry().getDuration(), properties.getHeaders(), webClient);
        this.properties = properties;
    }

    @Override
    public String getPersonAccounts(AccountInfoRequest request) {
        log.info("Старт вызова {}", PRODUCT_PROFILE_FL.getValue());

        properties.getHeaders().set(HEADER_NAME_X_MDM_ID, "5000015297");

        String accounts = this.post(uriBuilder -> uriBuilder.path(properties.getResource()).build(),
                request, String.class);

        SearchMasterAccount.getMainStringAccounts(accounts);

        log.info("Финиш вызова {}", PRODUCT_PROFILE_FL.getValue());
        return accounts;
    }

}
