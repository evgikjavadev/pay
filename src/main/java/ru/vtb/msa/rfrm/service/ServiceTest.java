package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.entitytodatabase.TaskStatusHistory;
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.personaccounts.PersonMasterAccount;
import ru.vtb.msa.rfrm.integration.personaccounts.client.PersonClientAccounts;

import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;

import ru.vtb.msa.rfrm.integration.util.client.ResponseObjWebClient;
import ru.vtb.msa.rfrm.repository.TaskStatusHistoryRepository;
import ru.vtb.omni.audit.lib.api.annotation.Audit;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceTest {

    private static final String MAIN_TEMPLATE_MASTER_ACCOUNT = "\".*MASTER_ACCOUNT-\\d+.*:\\s*\\s*[\\w{}а-яА-Я\\.\"\\,\\s*:\\_\\d-]+_ACCOUNT-\\d";
    private static final String TEMPLATE_ENTITY_TYPE = "\"entityType\"\\s*:\\s*\"MASTER_ACCOUNT\"";
    private static final String TEMPLATE_BALANCE_RUB = "\"balance\"\\s*:\\s*\\{\\s*\"currency\"\\s*:\\s*\"RUB\"";
    private static final String TEMPLATE_IS_ARRESTED = "\"isArrested\"\\s*:\\s*false";
    private static final String TEMPLATE_NUMBER = "\"number\"\\s*:\\s*\"(.*?)\"";
    private static final String TEMPLATE_NUMBER_ACCOUNT = "\\d+";
    private final PersonClientAccounts personClientAccounts;

    private final PersonMasterAccount personMasterAccount;

    private final String personAccounts = "";
    private final String headersPersonAccount = "";

    private final TaskStatusHistoryRepository repository;

    @Audit(value = "EXAMPLE_EVENT_CODE")
    //@PreAuthorize("permittedByRole('READ')")
    public void test() {

        try {

            // получаем номер счета клиента
//            String headersPersonAccountInit = personClientAccounts.getHeadersPersonAccount(sendRequestListAccounts(Collections.singletonList("ACCOUNT")));
//            headersPersonAccount.concat(headersPersonAccountInit);

            // получаем сам объект с данными счета клиента
            personClientAccounts.getPersonAccounts(sendRequestListAccounts(Collections.singletonList("ACCOUNT")));

//            String responseWebClient = responseObjWebClient.getResponseWebClient();
//            System.out.println("my89 = " + responseWebClient);


            //personAccounts.concat(accountObject);

        } catch (HttpStatusException e) {
          e.getStatus();
          sendObjectToTaskStatusHistory();
        }

        //String requestField = personMasterAccount.getRequestField(new StringBuilder(personAccounts), MAIN_TEMPLATE_MASTER_ACCOUNT);

        //System.out.println("my5 = " + requestField);

    }

    private void sendObjectToTaskStatusHistory() {
        TaskStatusHistory taskStatusHistory = getTaskStatusHistory();
        repository.save(taskStatusHistory);
    }

    /** Метод собирает объект для сохранения в БД */
    private static TaskStatusHistory getTaskStatusHistory() {
        TaskStatusHistory taskStatusHistory = TaskStatusHistory
                .builder()
                //.taskStatus(e.getStatus().value())
                .errorDetails(101)
                .statusUpdatedAt(LocalDateTime.now())
                .build();
        return taskStatusHistory;
    }

    private AccountInfoRequest sendRequestListAccounts(List<String> str) {
        return AccountInfoRequest.builder().productTypes(str).build();
    }
    
}
