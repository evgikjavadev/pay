package ru.vtb.msa.rfrm.integration.personcs.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.GetPersonRequest;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.GetPersonRs;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.PcRequest;
import ru.vtb.msa.rfrm.integration.personcs.config.ProductProfileFL;
import ru.vtb.msa.rfrm.integration.util.client.WebClientBase;

import java.util.UUID;

import static ru.vtb.msa.rfrm.integration.util.client.HeadersConstant.HEADER_NAME_X_CALL_ID;
import static ru.vtb.msa.rfrm.integration.util.enums.ClientName.PRODUCT_PROFILE_FL;

@Slf4j
public class PersonClientImpl extends WebClientBase implements PersonClient {

    private final ProductProfileFL properties;

    public PersonClientImpl(WebClient webClient, ProductProfileFL properties) {
        super(properties.getRetry().getCount(), properties.getRetry().getDuration(), properties.getHeaders(), webClient);
        this.properties = properties;
    }

    @Override
    public Object getPerson(PcRequest<GetPersonRequest> request) {
        log.info("Старт вызова {}", PRODUCT_PROFILE_FL.getValue());

        properties.getHeaders().set(HEADER_NAME_X_CALL_ID, UUID.randomUUID().toString());
        String personAccountsObject = this.post(uriBuilder -> uriBuilder.path(properties.getSearch()).build(),
                request, String.class);

//        if (!personAccountsObject.isEmpty() & account.entityType="MASTER_ACCOUNT" & account.balance.curency="RUB") {
//            if (account.isArrested) {
//                databaseRepository.edit(paymentTask=3)
//            }
//        }


        //System.out.println("personAccountsObject = " + personAccountsObject);

        log.info("Финиш вызова {}", PRODUCT_PROFILE_FL.getValue());
        return personAccountsObject;
    }
}
