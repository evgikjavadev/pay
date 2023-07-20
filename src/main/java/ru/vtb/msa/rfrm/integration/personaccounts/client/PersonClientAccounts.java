package ru.vtb.msa.rfrm.integration.personaccounts.client;

import org.springframework.http.ResponseEntity;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.responsenew.ResponseCommonWebClient;

public interface PersonClientAccounts {
    ResponseCommonWebClient getPersonAccounts(AccountInfoRequest request);

}
