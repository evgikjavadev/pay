package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.PayTaskStatusHistoryActions;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.PaymentTaskActionsDb;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntTaskStatusHistory;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.rfrmcore.model.ObjectRewardReq;
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
    private final PayTaskStatusHistoryActions payTaskStatusHistoryActions;

    // ------------------------------------------------------------------
    // получаем некоторые данные из ответа из объекта 1503
    Integer personAccountNumber = 1234567567;               //todo сделать поиск данных из объекта
    String currency = "RUB";
    String accountSystem = "OPENWAY";
    String mdmId = "12345678";
    Boolean isArrested = true;
    // -------------------------------------------------------------------

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

        handlerObjectPersonAccounts(personAccountNumber, currency, accountSystem, mdmId, isArrested);

    }

    private void handlerObjectPersonAccounts(Integer personAccountNumber, String currency, String accountSystem, String mdmId, Boolean isArrested) {

        if (personAccountNumber != null
                && currency.equals("RUB")
                && isArrested.equals(false)) {

            // получаем из БД объекты с mdmId который пришел из 1503
            List<EntPaymentTask> listTasks = paymentTaskActionsDb.getPaymentTaskByMdmId(mdmId);

            if (listTasks.size() == 1) {
                paymentTaskActionsDb.updateAccountNumber(personAccountNumber, accountSystem, mdmId);
            } else  {
                //todo   проработать с аналитиком этот вариант
            }
        }

        if (personAccountNumber != null
                && currency.equals("RUB")
                && isArrested.equals(true)) {

            //записать в БД для данного задания paymentTask.status=30
            Integer status = 30;
            paymentTaskActionsDb.updateStatus(mdmId, status);

            // формируем данные для сохранения в БД ent_task_status_history
            EntTaskStatusHistory entTaskStatusHistory = EntTaskStatusHistory
                    .builder()
                    .statusHistoryId(UUID.randomUUID())
                    .statusDetailsCode(202)                        // хард код: 202
                    .taskId(getPayPaymentTask().getRewardId())
                    .taskStatus(status)
                    .statusUpdatedAt(getPayPaymentTask().getCreatedAt())
                    .build();

            // создать новую запись в таблице taskStatusHistory
            payTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

            //Записать в топик Rewards-Res сообщение, содержащее id задания, status=30, status_details_code=202

            //Завершить обработку задания

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
    private EntPaymentTask getPayPaymentTask() {
        // обогащаем объект из топика RewardReq полями и создаем новый объект
        EntPaymentTask entPaymentTask = EntPaymentTask
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
        return entPaymentTask;
    }

    private AccountInfoRequest sendRequestListAccounts(List<String> str) {
        return AccountInfoRequest.builder().productTypes(str).build();
    }
    
}
