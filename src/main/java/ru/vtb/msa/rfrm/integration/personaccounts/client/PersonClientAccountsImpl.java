package ru.vtb.msa.rfrm.integration.personaccounts.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.ResponseCommon;
import ru.vtb.msa.rfrm.integration.personaccounts.config.ProductProfileFL;
import ru.vtb.msa.rfrm.integration.util.client.WebClientBase;

import java.util.UUID;

import static ru.vtb.msa.rfrm.integration.util.client.HeadersConstant.HEADER_NAME_X_CALL_ID;
import static ru.vtb.msa.rfrm.integration.util.enums.ClientName.PRODUCT_PROFILE_FL;

@Slf4j
public class PersonClientAccountsImpl extends WebClientBase implements PersonClientAccounts {
    private final ProductProfileFL properties;

    public PersonClientAccountsImpl(WebClient webClient, ProductProfileFL properties) {
        super(properties.getRetry().getCount(), properties.getRetry().getDuration(), properties.getHeaders(), webClient);
        this.properties = properties;
    }

    @Override
    public ResponseCommon getPersonAccounts(AccountInfoRequest request) throws HttpStatusException {
        log.info("Старт вызова {}", PRODUCT_PROFILE_FL.getValue());

        properties.getHeaders().set(HEADER_NAME_X_CALL_ID, UUID.randomUUID().toString());
        ResponseCommon accounts = this.post(uriBuilder -> uriBuilder.path(properties.getResource()).build(),
                request, ResponseCommon.class);


        //JSONPObject json = new JSONPObject()

//        try {
//            Object myObject = objectMapper.readValue(accounts, Object.class);
//
//            System.out.println("myObject = " + myObject);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        if (!personAccountsObject.isEmpty() & account.entityType="MASTER_ACCOUNT" & account.balance.curency="RUB") {
//            if (account.isArrested) {
//                databaseRepository.edit(paymentTask=3)
//            }
//        }


        //System.out.println("personAccountsObject = " + personAccountsObject);

        log.info("Финиш вызова {}", PRODUCT_PROFILE_FL.getValue());
        return accounts;
    }

}
