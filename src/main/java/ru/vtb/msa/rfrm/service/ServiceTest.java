package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.integration.personcs.client.PersonClient;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.ContactRequestModel;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.GetPersonRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.PcHeaderRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.PcRequest;

import ru.vtb.omni.audit.lib.api.annotation.Audit;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceTest {
    private final PersonClient personClient;

    @Audit(value = "EXAMPLE_EVENT_CODE")
    //@PreAuthorize("permittedByRole('READ')")
    public void test() {
          personClient.getPerson(createPersonRequest("1371988972", UUID.randomUUID().toString()));
       }

    private PcRequest<GetPersonRequest> createPersonRequest(String partyUId, String messageId) {
        return PcRequest.<GetPersonRequest>builder()
                .headerRequest(createHeaders(messageId))
                .messageRequest(createBodyForRequest(partyUId))
                .build();
    }

    private GetPersonRequest createBodyForRequest(String partyUId) {
        return GetPersonRequest.builder()
                .person(Collections.singletonList(ContactRequestModel.builder().partyUId(partyUId).build()))
                .build();
    }

    private PcHeaderRequest createHeaders(String messageId) {
        return PcHeaderRequest.builder()
                .contactName("VTB_MOBILE")
                .messageID(UUID.randomUUID().toString())
                .systemFrom("18...")
                .creationDateTime(Instant.now().toString())
                .timeout(0)
                .build();
    }
}
