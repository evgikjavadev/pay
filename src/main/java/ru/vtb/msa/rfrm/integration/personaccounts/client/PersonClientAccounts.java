package ru.vtb.msa.rfrm.integration.personaccounts.client;

import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.ResponseCommon;

public interface PersonClientAccounts {
    String getPersonAccounts(AccountInfoRequest request);

}
