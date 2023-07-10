package ru.vtb.msa.rfrm.integration.personaccounts.client;


import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.Response;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model.GetPersonRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model.Object;


public interface PersonClientAccounts {
    Response getPersonAccounts(Object request);
}
