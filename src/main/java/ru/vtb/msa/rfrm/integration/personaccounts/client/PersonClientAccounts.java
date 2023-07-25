package ru.vtb.msa.rfrm.integration.personaccounts.client;

import ru.vtb.msa.rfrm.integration.personaccounts.client.model.request.AccountInfoRequest;


public interface PersonClientAccounts {
    String getPersonAccounts(AccountInfoRequest request);

}
