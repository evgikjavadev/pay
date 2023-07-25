package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.PaymentTaskActionsDb;
import ru.vtb.msa.rfrm.integration.rfrmcore.model.ObjectRewardReq;
import ru.vtb.msa.rfrm.modeldatabase.PayPaymentTask;
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.personaccounts.client.PersonClientAccounts;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.request.AccountInfoRequest;
import ru.vtb.omni.audit.lib.api.annotation.Audit;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceAccounts {
    private final PersonClientAccounts personClientAccounts;
    private final PaymentTaskActionsDb paymentTaskActionsDb;

    //private final TaskStatusHistoryRepository repository;

    @SneakyThrows
    @Audit(value = "EXAMPLE_EVENT_CODE")
    //@PreAuthorize("permittedByRole('READ')")
    public void getClientAccounts() {

        String personAccounts = "";
        try {
            // получаем весь объект с данными счета клиента из 1503
            String personAccountsObject = personClientAccounts.getPersonAccounts(sendRequestListAccounts(Collections.singletonList("ACCOUNT")));
            personAccounts.concat(personAccountsObject);

        } catch (HttpStatusException e) {
          e.getStatus();
          //sendObjectToTaskStatusHistory();
        }

        // ------------------------------------------------------------------
        // получаем некоторые данные из ответа из объекта 1503
        Integer personAccountNumber = 1234567890;               //todo сделать поиск номера счета из объекта
        String accountSystem = "OPENWAY";
        String mdmId = "12345678";
        // -------------------------------------------------------------------

        // делаем запрос в БД с mdmId который пришел из 1503
        PayPaymentTask paymentTaskByMdmId = paymentTaskActionsDb.getPaymentTaskByMdmId(mdmId);

        // проверяем есть ли уже в БД такой клиент с mdmId который пришел из 1503
        if (paymentTaskByMdmId.getMdmId().equals(mdmId)) {
            // обновляем данные полей account и accountSystem данными из 1503
            paymentTaskActionsDb.updateAccountNumber(personAccountNumber, accountSystem);
        } else {
            throw new Exception("Клиент с mdmId нет в БД");   //todo    hand exceptions
        }


        if (!personAccountNumber.equals(0)) {

        }


    }

    // собираем тестовый объект который пришел из кафка топика RewardReq
    public ObjectRewardReq getObjectRewardReqFromKafka() {
        ObjectRewardReq objectRewardReq = ObjectRewardReq
                .builder()
                .id(UUID.randomUUID())
                .money(6500.00)
                .mdmId("12345678")
                .questionnaire_id(UUID.randomUUID())
                .recipientType(3)
                .source_qs("stringSourceQs")
                .build();
        return objectRewardReq;
    }




    public void saveNewTaskToPayPaymentTask() {

        paymentTaskActionsDb.insertPaymentTaskInDB(getPayPaymentTask());

    }

    // создаем тестовый объект PayPaymentTask
    private PayPaymentTask getPayPaymentTask() {
        // обогащаем объект из топика RewardReq полями и создаем новый объект
        PayPaymentTask payPaymentTask = PayPaymentTask
                .builder()
                .rewardId(UUID.randomUUID())                            // из топика кафка RewardReq
                .questionnaireId(UUID.randomUUID())                    //todo берем из 1642 1642 Платформа анализа и обработки данных (Data Analysis and Processing Platform)
                .mdmId(getObjectRewardReqFromKafka().getMdmId())                     //берем из кафка RewardReq
                .recipientType(getObjectRewardReqFromKafka().getRecipientType())         // берем из кафка RewardReq
                .amount(getObjectRewardReqFromKafka().getMoney())                       // берем из кафка RewardReq
                .status(10)                                            // автоматически дополняем
                .createdAt(LocalDateTime.now())                        // автоматически дополняем
                .responseSent(false)                                  // автоматически дополняем
                .sourceQs(getObjectRewardReqFromKafka().getSource_qs())             // берем из кафка RewardReq
                .account(38787798)                                      // берем из 1503
                .accountSystem("OPENWAY")                               // берем из 1503
                .build();
        return payPaymentTask;
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
