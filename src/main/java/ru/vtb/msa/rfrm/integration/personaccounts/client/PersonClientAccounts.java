package ru.vtb.msa.rfrm.integration.personaccounts.client;

import ru.vtb.msa.rfrm.integration.personaccounts.client.model.request.AccountInfoRequest;

import java.util.Map;


public interface PersonClientAccounts {
    Map<String, Object> getPersonAccounts(AccountInfoRequest request);

}
