package ru.vtb.msa.rfrm.service;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.EntTaskStatusHistoryActions;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.DctStatusDetails;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntTaskStatusHistory;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.rfrmcore.model.ObjectRewardReq;
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.personaccounts.client.PersonClientAccounts;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.request.AccountInfoRequest;
import ru.vtb.omni.audit.lib.api.annotation.Audit;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceAccounts {
    private final PersonClientAccounts personClientAccounts;
    private final EntPaymentTaskActions entPaymentTaskActions;
    private final EntTaskStatusHistoryActions entTaskStatusHistoryActions;
    String personAccounts = "";
    private final HikariDataSource hikariDataSource;

    // получаем некоторые данные из ответа из объекта 1503        //todo сделать поиск данных из объекта
    String personAccountNumber = "1234567567";
    String currency = "RUB";
    String accountSystem = "OPENWAY";
    String mdmId = "12345678";
    Boolean isArrested = false;
    String result = "ok";

    // получаем данные из топика кафка
    private final UUID uuidFromKafka = UUID.randomUUID();
    private final String mdmIdFromKafka = "12345678";
    private final String sourceQs = "sourceQsExample";
    private final Double sumReward = 6500.00;
    private final Integer recipientType = 3;
    private final UUID questionnaireId = UUID.randomUUID();
    // -------------------------------------------------------------------

    @SneakyThrows
    @Audit(value = "EXAMPLE_EVENT_CODE")
    //@PreAuthorize("permittedByRole('READ')")
    public void getClientAccounts() {

        try {
            // получаем весь объект с данными счета клиента из 1503
            String personAccountsObject = personClientAccounts
                    .getPersonAccounts(sendRequestListAccounts(Collections.singletonList("ACCOUNT")));
            personAccounts.concat(personAccountsObject);

        } catch (HttpStatusException e) {
          e.getStatus();
          //sendObjectToTaskStatusHistory();   //todo    обраюотать exception
        }

        handlerObjectPersonAccounts(personAccountNumber, currency, accountSystem, mdmId, isArrested);

    }

    @SneakyThrows
    private void handlerObjectPersonAccounts(String personAccountNumber, String currency, String accountSystem, String mdmId, Boolean isArrested) {
        Connection connection = null;
        if (personAccountNumber != null
                && currency.equals("RUB")
                && isArrested.equals(false)) {

            // получаем из БД объекты с mdmId который пришел из 1503 и уже сохранен в БД
            List<EntPaymentTask> listTasks = entPaymentTaskActions.getPaymentTaskByMdmId(mdmId);

            if (listTasks.size() == 1) {

                try {
                    connection = hikariDataSource.getConnection();

                    connection.setAutoCommit(false);

                    // Записать в БД для данного задания paymentTask.status=50
                    entPaymentTaskActions
                            .updateAccountNumber(
                                    personAccountNumber,
                                    accountSystem,
                                    mdmId,
                                    DctTaskStatuses.STATUS_READY_FOR_PAYMENT.getStatus()
                            );

                    // формируем объект для записи в табл. taskStatusHistory
                    EntTaskStatusHistory entTaskStatusHistory =
                            createEntTaskStatusHistory(DctTaskStatuses.STATUS_READY_FOR_PAYMENT.getStatus(), null);

                    // создать новую запись в таблице taskStatusHistory
                    entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

                    connection.commit();

                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        assert connection != null;
                        connection.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            } else  {
                //todo   проработать с аналитиком, он сказал что не может быть 2 задания
            }

        }

        if (personAccountNumber != null
                && currency.equals("RUB")
                && isArrested.equals(true)) {

            try {
                connection = hikariDataSource.getConnection();

                connection.setAutoCommit(false);

                //обновляем в БД для данного задания paymentTask.status=30
                entPaymentTaskActions.updateStatus(mdmId, DctTaskStatuses.STATUS_REJECTED.getStatus());

                // формируем данные для сохранения в БД ent_task_status_history
                EntTaskStatusHistory entTaskStatusHistory = createEntTaskStatusHistory(
                        DctTaskStatuses.STATUS_REJECTED.getStatus(),
                        DctStatusDetails.MASTER_ACCOUNT_ARRESTED.getStatusDetailsCode()
                );

                // создать новую запись в таблице taskStatusHistory
                entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

                connection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    assert connection != null;
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            //Записать в топик Rewards-Res сообщение, содержащее id задания, status=30, status_details_code=202   //todo

        }

        if (personAccountNumber == null && result.equals("ok")) {

            try {
                connection = hikariDataSource.getConnection();

                connection.setAutoCommit(false);

                // Записать в БД для данного задания paymentTask.status=30,
                entPaymentTaskActions.updateStatus(mdmId, DctTaskStatuses.STATUS_REJECTED.getStatus());

                //  создать новую запись в таблице taskStatusHistory с taskStatusHistory.status_details=201
                EntTaskStatusHistory entTaskStatusHistory = createEntTaskStatusHistory(DctTaskStatuses.STATUS_REJECTED.getStatus(),
                        DctStatusDetails.MASTER_ACCOUNT_NOT_FOUND.getStatusDetailsCode());
                entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

                connection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    assert connection != null;
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            // Записать в топик Rewards-Res сообщение, содержащее id задания и status=30, status_details_code=201   //todo

        }

    }

    private EntTaskStatusHistory createEntTaskStatusHistory(Integer taskStatus, Integer statusDetailsCode) {
        return EntTaskStatusHistory
                .builder()
                .rewardId(createPayPaymentTask().getRewardId())
                .statusDetailsCode(statusDetailsCode)
                .taskStatus(taskStatus)
                .statusUpdatedAt(createPayPaymentTask().getCreatedAt())
                .build();
    }


    // собираем тестовый объект который пришел из кафка топика RewardReq
    public ObjectRewardReq getObjectRewardReqFromKafka() {
        return ObjectRewardReq
                .builder()
                .id(uuidFromKafka)
                .money(sumReward)
                .mdmId(mdmIdFromKafka)
                .questionnaire_id(questionnaireId)
                .recipientType(recipientType)
                .source_qs(sourceQs)
                .build();
    }

    public void saveNewTaskToPayPaymentTask() {
        entPaymentTaskActions.insertPaymentTaskInDB(createPayPaymentTask());
    }

    // создаем тестовый объект PayPaymentTask
    private EntPaymentTask createPayPaymentTask() {
        // обогащаем объект из топика RewardReq полями и создаем новый объект
        EntPaymentTask entPaymentTask = EntPaymentTask
                .builder()
                .rewardId(uuidFromKafka)                            // из топика кафка RewardReq
                .questionnaireId(UUID.randomUUID())                    //todo берем из 1642 1642 Платформа анализа и обработки данных (Data Analysis and Processing Platform)
                .mdmId(getObjectRewardReqFromKafka().getMdmId())                     //берем из кафка RewardReq
                .recipientType(getObjectRewardReqFromKafka().getRecipientType())      // берем из кафка RewardReq
                .amount(getObjectRewardReqFromKafka().getMoney())                     // берем из кафка RewardReq
                .status(10)                                                         // автоматически дополняем
                .createdAt(LocalDateTime.now())                                     // автоматически дополняем
                .sourceQs(getObjectRewardReqFromKafka().getSource_qs())             // берем из кафка RewardReq
                .account(String.valueOf(38787798))                               // берем из 1503
                .accountSystem("OPENWAY")                                           // берем из 1503
                .build();
        return entPaymentTask;
    }

    private AccountInfoRequest sendRequestListAccounts(List<String> str) {
        return AccountInfoRequest.builder().productTypes(str).build();
    }
    
}
