package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.PaymentTaskActions;
import ru.vtb.msa.rfrm.integration.rfrmcore.model.ObjectRewardReq;
import ru.vtb.msa.rfrm.modeldatabase.PayPaymentTask;
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


        } catch (HttpStatusException e) {
          e.getStatus();
          //sendObjectToTaskStatusHistory();
        }

    }

    public void saveNewTaskToPayPaymentTask() {

        //тестовый объект который получаем из Кафка с заполненными полями
        ObjectRewardReq objectRewardReq = ObjectRewardReq
                .builder()
                .id(UUID.randomUUID())
                .money(6500.00)
                .mdmId("12345678")
                .questionnaire_id(UUID.randomUUID())
                .recipientType(3)
                .source_qs("stringSourceQs")
                .build();

        // обогащаем объект из топика RewardReq полями и создаем новый объект
        PayPaymentTask payPaymentTask = PayPaymentTask
                .builder()
                .rewardId(UUID.randomUUID())                            // из топика кафка RewardReq
                .questionnaireId(UUID.randomUUID())                    //todo берем из 1642 1642 Платформа анализа и обработки данных (Data Analysis and Processing Platform)
                .mdmId(objectRewardReq.getMdmId())                     //берем из кафка RewardReq
                .recipientType(objectRewardReq.getRecipientType())         // берем из кафка RewardReq
                .amount(objectRewardReq.getMoney())                       // берем из кафка RewardReq
                .status(10)                                            // автоматически дополняем
                .createdAt(LocalDateTime.now())                        // автоматически дополняем
                .responseSent(false)                                  // автоматически дополняем
                .sourceQs(objectRewardReq.getSource_qs())             // берем из кафка RewardReq
                .account(38787798)                                   // берем из 1503
                .accountSystem("1503")                               //todo  узнать что писать в поле как называется система 1503
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
