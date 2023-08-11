package ru.vtb.msa.rfrm.service;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.EntTaskStatusHistoryActions;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.DctStatusDetails;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntTaskStatusHistory;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Account;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Response;
import ru.vtb.msa.rfrm.integration.personaccounts.client.PersonClientAccounts;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.request.AccountInfoRequest;
import ru.vtb.omni.audit.lib.api.annotation.Audit;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ServiceAccounts {
    private final PersonClientAccounts personClientAccounts;
    private final EntPaymentTaskActions entPaymentTaskActions;
    private final EntTaskStatusHistoryActions entTaskStatusHistoryActions;
    private final HikariDataSource hikariDataSource;

    @SneakyThrows
    @Audit(value = "EXAMPLE_EVENT_CODE")
    //@PreAuthorize("permittedByRole('READ')")    //todo
    public void getClientAccounts(String mdmIdFromKafka) {

        // получаем весь объект с данными счета клиента из 1503
        Response<?> personAccounts = personClientAccounts
                .getPersonAccounts(mdmIdFromKafka, sendRequestListAccounts(Collections.singletonList("ACCOUNT")));

        // получаем номер счета в ответе от 1503
        String personAccountNumber = personAccounts
                .getBody()
                .getAccounts()
                .values()
                .stream()
                .map(Account::getNumber)
                .findFirst()
                .get();

        // получаем currency в ответе от 1503
        String currency = personAccounts
                .getBody()
                .getAccounts()
                .values()
                .stream()
                .map(a -> a.getBalance().getCurrency())
                .findFirst()
                .get();

        // ищем accountSystem (getEntitySubSystems) в ответе от 1503
        String accountSystem = personAccounts
                .getBody()
                .getAccounts()
                .values()
                .stream()
                .map(Account::getEntitySubSystems)
                .findFirst()
                .get();

        // получаем isArrested в ответе от 1503
        Boolean isArrested = personAccounts
                .getBody()
                .getAccounts()
                .values()
                .stream()
                .map(Account::getIsArrested)
                .findFirst()
                .get();

        // получаем значение result
        String result = personAccounts.getBody().getResult();

        // получаем mdmId из заголовков ответа от 1503
        String mdmId = personAccounts
                .getHeaders()
                .entrySet()
                .stream()
                .filter(a -> a.getKey().equals("X-Mdm-Id"))
                .findFirst()
                .orElseThrow()
                .getValue()
                .get(0);

        handlerObjectPersonAccounts(personAccountNumber, currency, accountSystem, mdmId, isArrested, result);

    }

    @SneakyThrows
    private void handlerObjectPersonAccounts(String personAccountNumber,
                                             String currency,
                                             String accountSystem,
                                             String mdmId,
                                             Boolean isArrested,
                                             String result) {

        // получаем из БД объекты с mdmId который пришел из 1503 и уже сохранен в БД
        List<EntPaymentTask> listTasks = entPaymentTaskActions.getPaymentTaskByMdmId(mdmId);
        UUID rewardId = listTasks.get(0).getRewardId();

        Connection connection = null;

        if (personAccountNumber != null
                && currency.equals("RUB")
                && isArrested.equals(false)) {

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
                            createEntTaskStatusHistory(DctTaskStatuses.STATUS_READY_FOR_PAYMENT.getStatus(), null, rewardId);

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
            }
        }

        if (personAccountNumber == null || personAccountNumber.isEmpty()) {

                try {
                    connection = hikariDataSource.getConnection();
                    connection.setAutoCommit(false);

                    // изменяем статус задания на "отклонено" (paymentTask.status=30)
                    entPaymentTaskActions
                            .updateAccountNumber(
                                    personAccountNumber,
                                    accountSystem,
                                    mdmId,
                                    DctTaskStatuses.STATUS_REJECTED.getStatus()
                            );

                    // формируем объект для записи в табл. taskStatusHistory
                    EntTaskStatusHistory entTaskStatusHistory =
                            createEntTaskStatusHistory(DctTaskStatuses.STATUS_REJECTED.getStatus(), null, rewardId);

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
                        DctStatusDetails.MASTER_ACCOUNT_ARRESTED.getStatusDetailsCode(),
                        rewardId
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

            //Записать в топик rfrm_pay_result_reward сообщение, содержащее id задания, status=30, status_details_code=202   //todo

        }

        if (personAccountNumber == null
                || personAccountNumber.equals("")
                || personAccountNumber.isEmpty()
                && result.equals("ok")) {

            try {
                connection = hikariDataSource.getConnection();
                connection.setAutoCommit(false);

                // Записать в БД для данного задания paymentTask.status=30,
                entPaymentTaskActions.updateStatus(mdmId, DctTaskStatuses.STATUS_REJECTED.getStatus());

                //  создать новую запись в таблице taskStatusHistory с taskStatusHistory.status_details=201
                EntTaskStatusHistory entTaskStatusHistory =
                        createEntTaskStatusHistory(
                            DctTaskStatuses.STATUS_REJECTED.getStatus(),
                            DctStatusDetails.MASTER_ACCOUNT_NOT_FOUND.getStatusDetailsCode(),
                            rewardId
                        );
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

            // Записать в топик rfrm_pay_result_reward сообщение, содержащее id задания и status=30, status_details_code=201   //todo
            //kafkaTemplate.send("rfrm_pay_result_reward", obj);


        }

    }

    private EntTaskStatusHistory createEntTaskStatusHistory(Integer taskStatus, Integer statusDetailsCode, UUID rewardId) {
        return EntTaskStatusHistory
                .builder()
                    .rewardId(rewardId)
                    .statusDetailsCode(statusDetailsCode)
                    .taskStatus(taskStatus)
                    .statusUpdatedAt(LocalDateTime.now())
                .build();
    }

    // метод сохраняет объект в БД если совпадений по rewardId не найдено
    public void saveNewTaskToPayPaymentTask(EntPaymentTask entPaymentTask) {

        if (entPaymentTaskActions.getPaymentTaskByRewardId(entPaymentTask.getRewardId()).size() == 0) {
            entPaymentTaskActions.insertPaymentTaskInDB(entPaymentTask);
        }

    }

    private AccountInfoRequest sendRequestListAccounts(List<String> str) {
        return AccountInfoRequest.builder().productTypes(str).build();
    }
    
}
