package ru.vtb.msa.rfrm.integration.personcs.client;


import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.GetPersonRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.PcRequest;


public interface PersonClient {
    Object getPerson(PcRequest<GetPersonRequest> request);
}
