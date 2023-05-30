package ru.vtb.msa.rfrm.integration.personcs.client;

import org.springframework.http.HttpStatus;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.closephoneinputmodel.ClosePhoneMessageRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.closephoneinputmodel.http.ClosePhonePcRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.updatephoneinputmodel.UpdatePhoneMessageRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.updatephoneinputmodel.http.UpdatePhonePcRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.GetPersonRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.GetPersonRs;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.PcRequest;


public interface PersonClient {
    GetPersonRs getPerson(PcRequest<GetPersonRequest> request);
    HttpStatus updatePhone(UpdatePhonePcRequest<UpdatePhoneMessageRequest> request);
    HttpStatus closePhone(ClosePhonePcRequest<ClosePhoneMessageRequest> request);
}
