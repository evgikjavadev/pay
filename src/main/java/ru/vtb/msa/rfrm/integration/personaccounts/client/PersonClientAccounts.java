package ru.vtb.msa.rfrm.integration.personaccounts.client;

import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;

import java.util.Map;

public interface PersonClientAccounts {
    Map<String, Map<String, String>> getPersonAccounts(AccountInfoRequest request);

}
