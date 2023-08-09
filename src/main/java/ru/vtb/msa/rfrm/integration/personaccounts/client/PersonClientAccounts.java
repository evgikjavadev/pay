package ru.vtb.msa.rfrm.integration.personaccounts.client;

import ru.vtb.msa.rfrm.integration.personaccounts.client.model.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Response;


public interface PersonClientAccounts {
    Response<?> getPersonAccounts(String mdmIdFromKafka, AccountInfoRequest request);

}
