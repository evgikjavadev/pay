package ru.vtb.msa.rfrm.integration.personcs.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.closephoneinputmodel.ClosePhoneMessageRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.closephoneinputmodel.http.ClosePhonePcRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.updatephoneinputmodel.UpdatePhoneMessageRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.updatephoneinputmodel.http.UpdatePhonePcRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.GetPersonRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.GetPersonRs;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.PcRequest;
import ru.vtb.msa.rfrm.integration.personcs.config.PersonClientProperties;
import ru.vtb.msa.rfrm.integration.util.client.WebClientBase;

import java.util.UUID;

import static ru.vtb.msa.rfrm.integration.util.client.HeadersConstant.HEADER_NAME_X_CALL_ID;
import static ru.vtb.msa.rfrm.integration.util.enums.ClientName.PERSON_CS;

@Slf4j
public class PersonClientImpl extends WebClientBase implements PersonClient {

    private final PersonClientProperties properties;

    public PersonClientImpl(WebClient webClient, PersonClientProperties properties) {
        super(properties.getRetry().getCount(), properties.getRetry().getDuration(), properties.getHeaders(), webClient);
        this.properties = properties;
    }

    @Override
    public GetPersonRs getPerson(PcRequest<GetPersonRequest> request) {
        log.info("Старт вызова {}", PERSON_CS.getValue());
        properties.getHeaders().set(HEADER_NAME_X_CALL_ID, UUID.randomUUID().toString());
        GetPersonRs personRs = this.post(uriBuilder -> uriBuilder.path(properties.getGetPerson()).build(),
                request, GetPersonRs.class);
        log.info("Финиш вызова {}", PERSON_CS.getValue());
        return personRs;
    }

    @SneakyThrows
    @Override
    public HttpStatus updatePhone(UpdatePhonePcRequest<UpdatePhoneMessageRequest> request) {
        log.info("Старт вызова {}", PERSON_CS.getValue());
        properties.getHeaders().set(HEADER_NAME_X_CALL_ID, UUID.randomUUID().toString());
        HttpStatus status = this.post(uriBuilder -> uriBuilder.path(properties.getUpdatePhone()).build(),
                request);
        log.info("Финиш вызова {}", PERSON_CS.getValue());
        return status;
    }

    @Override
    public HttpStatus closePhone(ClosePhonePcRequest<ClosePhoneMessageRequest> request) {
        log.info("Старт вызова {}", PERSON_CS.getValue());
        properties.getHeaders().set(HEADER_NAME_X_CALL_ID, UUID.randomUUID().toString());
        HttpStatus httpStatus = this.post(uriBuilder -> uriBuilder.path(properties.getUpdatePhone()).build(),
                request);
        log.info("Финиш вызова {}", PERSON_CS.getValue());
        return httpStatus;
    }
}
