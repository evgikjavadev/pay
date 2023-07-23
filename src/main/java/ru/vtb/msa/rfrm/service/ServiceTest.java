package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.PaymentTaskActions;
import ru.vtb.msa.rfrm.entitytodatabase.PayPaymentTask;
import ru.vtb.msa.rfrm.entitytodatabase.PayTaskStatusHistory;
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.personaccounts.client.PersonClientAccounts;

import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.AccountInfoRequest;

import ru.vtb.omni.audit.lib.api.annotation.Audit;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceTest {
    private final PersonClientAccounts personClientAccounts;

    private final PaymentTaskActions paymentTaskActions;


    //private final TaskStatusHistoryRepository repository;

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
          //sendObjectToTaskStatusHistory();
        }

        //String requestField = personMasterAccount.getRequestField(new StringBuilder(personAccounts), MAIN_TEMPLATE_MASTER_ACCOUNT);

        //System.out.println("my5 = " + requestField);

    }

    public void saveNewTaskToDb() {

        PayPaymentTask payPaymentTask = PayPaymentTask.builder()
                .mdmId("675456")
                .rewardId(UUID.randomUUID())
                .account(38787798)
                .accountSystem("system")
                .recipientType(4)
                .responseSent(false)
                .amount(6700.00)
                .status(2)
                .createdAt(LocalDateTime.now())
                .sourceQs("89")
                .questionnaireId(UUID.randomUUID())
                .build();

        paymentTaskActions.createPaymentTask(payPaymentTask);

    }

//    private void sendObjectToTaskStatusHistory() {
//        TaskStatusHistory taskStatusHistory = getTaskStatusHistory();
//        repository.save(taskStatusHistory);
//    }

    /** Метод собирает объект для сохранения в БД */
//    private static PayTaskStatusHistory getTaskStatusHistory() {
//        PayTaskStatusHistory payTaskStatusHistory = PayTaskStatusHistory
//                .builder()
//                //.taskStatus(e.getStatus().value())
//                .errorDetails(101)
//                .statusUpdatedAt(LocalDateTime.now())
//                .build();
//        return payTaskStatusHistory;
//    }

    private AccountInfoRequest sendRequestListAccounts(List<String> str) {
        return AccountInfoRequest.builder().productTypes(str).build();
    }
    
}
