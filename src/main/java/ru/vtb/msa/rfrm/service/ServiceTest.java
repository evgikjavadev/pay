package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.integration.arbitration.client.ArbitrationClient;
import ru.vtb.msa.rfrm.integration.arbitration.dto.request.ArbitrationRequest;
import ru.vtb.msa.rfrm.integration.checking.client.ComplexCheckClient;
import ru.vtb.msa.rfrm.integration.checking.dto.request.ComplexCheckRequest;
import ru.vtb.msa.rfrm.integration.checking.dto.request.HeaderInfo;
import ru.vtb.msa.rfrm.integration.checking.dto.request.ServiceInfo;
import ru.vtb.msa.rfrm.integration.checking.dto.request.SubjectInfo;
import ru.vtb.msa.rfrm.integration.employee.client.EmployeeClient;
import ru.vtb.msa.rfrm.integration.personcs.client.PersonClient;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.ContactRequestModel;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.GetPersonRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.PcHeaderRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.PcRequest;
import ru.vtb.msa.rfrm.integration.session.SessionClient;
import ru.vtb.omni.audit.lib.api.annotation.Audit;
import ru.vtb.omni.jwt.servlet.service.ServletJwtService;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceTest {

    private final SessionClient sessionClient;
    private final ServletJwtService jwtService;
    private final EmployeeClient employeeClient;
    private final ComplexCheckClient complexCheckClient;

    private final ArbitrationClient  arbitrationClient;

    private final PersonClient personClient;

    @Audit(value = "EXAMPLE_EVENT_CODE")
    @PreAuthorize("permittedByRole('READ')")
    public void test() {

        sessionClient.getSessionMap(jwtService.extractTokenPayloadDto().getCtxi());
        employeeClient.getEmployee("1234567890");
        personClient.getPerson(createPersonRequest("1371988972", UUID.randomUUID().toString()));
        complexCheckClient.check(createCheckRequest());
        arbitrationClient.call(ArbitrationRequest.builder().build());
    }

    private ComplexCheckRequest createCheckRequest() {
       return ComplexCheckRequest.builder()
                .headerInfo(getHeaderInfo("123123123"))
                .serviceInfo(getServiceInfo("Test test tes", "empty@vtb.ru"))
                .subjectInfoList(List.of(SubjectInfo.builder().build()))
                .build();
    }


    private HeaderInfo getHeaderInfo(String sessionId) {
        return HeaderInfo.builder()
                .requestId(UUID.randomUUID().toString())
                .consumerId("18.._RFRM")
                .clientIp("127.0.0.1")
                .sessionId(sessionId)
                .processId("RFRM_SALES")
                .build();
    }

    private ServiceInfo getServiceInfo(String employeeFullName, String employeeEmail) {
        return ServiceInfo.builder()
                .entryChannel("SP")
                .product("18.._RFRM")
                .currency("RUB")
                .employeeFullname(employeeFullName)
                .employeeEmail(employeeEmail)
                .build();
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
