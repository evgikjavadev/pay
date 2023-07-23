package ru.vtb.msa.rfrm.integration.personaccounts.client;

import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;

public interface PersonClientAccounts {
    Object getPersonAccounts(AccountInfoRequest request);

}
