package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.integration.personaccounts.client.PersonClientAccounts;

import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model.ContactRequestModel;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model.GetPersonRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model.Object;

import ru.vtb.omni.audit.lib.api.annotation.Audit;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ServiceTest {
    private final PersonClientAccounts personClientAccounts;

    //private final MessageResponseInterface messageResponseInterface;

    @Audit(value = "EXAMPLE_EVENT_CODE")
    //@PreAuthorize("permittedByRole('READ')")
    public void test() {
          personClientAccounts.getPersonAccounts(createPersonRequest("1371988972"));

        //System.out.println("messageResponse = " + messageResponseInterface.getResponse().toString());

       }

    private Object<GetPersonRequest> createPersonRequest(String partyUId) {
        return Object.<GetPersonRequest>builder()
                //.headerRequest(createHeaders(messageId))
                .productTypes(createBodyForRequest(partyUId))
                .build();
    }

    private GetPersonRequest createBodyForRequest(String partyUId) {
        return GetPersonRequest.builder()
                .person(Collections.singletonList(ContactRequestModel.builder().partyUId(partyUId).build()))
                .build();
    }

//    private PcHeaderRequest createHeaders(String messageId) {
//        return PcHeaderRequest.builder()
//                .accounts(Collections.singletonList("ACCOUNT"))
//                //.messageID(UUID.randomUUID().toString())
//                //.systemFrom("18...")
//                //.creationDateTime(Instant.now().toString())
//                //.timeout(0)
//                .build();
//    }
}
