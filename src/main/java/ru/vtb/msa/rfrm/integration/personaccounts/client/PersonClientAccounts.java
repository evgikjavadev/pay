package ru.vtb.msa.rfrm.integration.personaccounts.client;

import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.ResponseCommon;

public interface PersonClientAccounts {
    ResponseCommon getPersonAccounts(AccountInfoRequest request);

}
