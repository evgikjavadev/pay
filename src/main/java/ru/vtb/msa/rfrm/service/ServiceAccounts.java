package ru.vtb.msa.rfrm.service;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.EntTaskStatusHistoryActions;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.DctStatusDetails;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntTaskStatusHistory;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Account;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Response;
import ru.vtb.msa.rfrm.integration.personaccounts.client.PersonClientAccounts;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayResultReward;
import ru.vtb.omni.audit.lib.api.annotation.Audit;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceAccounts {
    private final PersonClientAccounts personClientAccounts;
    private final EntPaymentTaskActions entPaymentTaskActions;
    private final EntTaskStatusHistoryActions entTaskStatusHistoryActions;
    private final HikariDataSource hikariDataSource;
    @Value("${process.platform.kafka.bootstrap.server}")
    private String bootstrapServers;
    private String topicResult = "rfrm_pay_result_reward";

    @SneakyThrows
    @Audit(value = "EXAMPLE_EVENT_CODE")
    //@PreAuthorize("permittedByRole('READ')")              //todo
    public void getClientAccounts(String mdmIdFromKafka) {

        try {
            // получаем весь объект с данными счета клиента из 1503
            Response<?> personAccounts = personClientAccounts
                    .getPersonAccounts(mdmIdFromKafka, sendRequestListAccounts(Collections.singletonList("ACCOUNT")));
            getResponseParam(personAccounts);

        } catch (HttpStatusException e) {
            HttpStatus status = e.getStatus();
            handleResponseHttpStatuses(status, mdmIdFromKafka);
        }

    }

    private void handleResponseHttpStatuses(HttpStatus status, String mdmIdFromKafka) {

        // найдем в табл. ent_payment_task rewardId по mdmId
        UUID rewardId = entPaymentTaskActions.
                getPaymentTaskByMdmId(mdmIdFromKafka)
                .get(0)
                .getRewardId();


        if (status.value() == 404) {

            // формируем объект для табл. taskStatusHistory
            EntTaskStatusHistory entTaskStatusHistory = EntTaskStatusHistory
                    .builder()
                    .rewardId(rewardId)
                    .statusDetailsCode(DctStatusDetails.CLIENT_NOT_FOUND_IN_MDM.getStatusDetailsCode())
                    .taskStatus(DctTaskStatuses.STATUS_MANUAL_PROCESSING.getStatus())
                    .statusUpdatedAt(LocalDateTime.now())
                    .build();

            Connection connection = null;

            try {
                connection = hikariDataSource.getConnection();
                connection.setAutoCommit(false);

                // обновляем табл. ent_payment_task
                entPaymentTaskActions.updateStatusEntPaymentTaskByRewardId(rewardId, DctTaskStatuses.STATUS_MANUAL_PROCESSING.getStatus());

                // создать новую запись в таблице taskStatusHistory с taskStatusHistory.status_details_code=101
                entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    assert connection != null;
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            // отправить сообщение об ошибке, требующей ручного разбора, в мониторинг
            //todo  отправить сообщение об ошибке, требующей ручного разбора, в мониторинг

        }
    }

    private void getResponseParam(Response<?> personAccounts) {
        // получаем номер счета в ответе от 1503
        String personAccountNumber = personAccounts
                .getBody()
                .getAccounts()
                .values()
                .stream()
                .map(Account::getNumber)
                .findFirst()
                .orElse("");

        // получаем currency в ответе от 1503
        String currency = personAccounts
                .getBody()
                .getAccounts()
                .values()
                .stream()
                .map(a -> a.getBalance().getCurrency())
                .findFirst()
                .orElse("");

        // ищем accountSystem (getEntitySubSystems) в ответе от 1503
        String accountSystem = personAccounts
                .getBody()
                .getAccounts()
                .values()
                .stream()
                .map(Account::getEntitySubSystems)
                .findFirst()
                .orElse("");

        // получаем isArrested в ответе от 1503
        Boolean isArrested = personAccounts
                .getBody()
                .getAccounts()
                .values()
                .stream()
                .map(Account::getIsArrested)
                .findFirst()
                .orElse(null);

        // получаем значение result в ответе от 1503
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

        handlePersonAccounts(personAccountNumber, currency, accountSystem, mdmId, isArrested, result);
    }

    @SneakyThrows
    private void handlePersonAccounts(String personAccountNumber,
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
                // формируем объект для записи в табл. taskStatusHistory
                EntTaskStatusHistory entTaskStatusHistory =
                        createEntTaskStatusHistory(DctTaskStatuses.STATUS_READY_FOR_PAYMENT.getStatus(), null, rewardId);

                try {
                    connection = hikariDataSource.getConnection();
                    connection.setAutoCommit(false);

                    // Записать в БД для данного задания paymentTask.status=50
                    entPaymentTaskActions.updateAccountNumber(
                                    personAccountNumber,
                                    accountSystem,
                                    mdmId,
                                    DctTaskStatuses.STATUS_READY_FOR_PAYMENT.getStatus()
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
            }
        }

        if (personAccountNumber == null || personAccountNumber.isEmpty()) {
            // формируем объект для записи в табл. taskStatusHistory
            EntTaskStatusHistory entTaskStatusHistory =
                    createEntTaskStatusHistory(DctTaskStatuses.STATUS_REJECTED.getStatus(), null, rewardId);

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

            // формируем данные для сохранения в БД ent_task_status_history
            EntTaskStatusHistory entTaskStatusHistory = createEntTaskStatusHistory(
                    DctTaskStatuses.STATUS_REJECTED.getStatus(),
                    DctStatusDetails.MASTER_ACCOUNT_ARRESTED.getStatusDetailsCode(),
                    rewardId
            );

            try {
                connection = hikariDataSource.getConnection();
                connection.setAutoCommit(false);

                //обновляем в БД для данного задания paymentTask.status=30
                entPaymentTaskActions.updateStatusEntPaymentTaskByMdmId(mdmId, DctTaskStatuses.STATUS_REJECTED.getStatus());

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

        if (personAccountNumber == null
                || personAccountNumber.equals("")
                || personAccountNumber.isEmpty()
                && result.equals("ok")) {

            //  создать новую запись в таблице taskStatusHistory с taskStatusHistory.status_details=201
            EntTaskStatusHistory entTaskStatusHistory =
                    createEntTaskStatusHistory(
                            DctTaskStatuses.STATUS_REJECTED.getStatus(),
                            DctStatusDetails.MASTER_ACCOUNT_NOT_FOUND.getStatusDetailsCode(),
                            rewardId
                    );

            try {
                connection = hikariDataSource.getConnection();
                connection.setAutoCommit(false);

                // Записать в БД для данного задания paymentTask.status=30,
                entPaymentTaskActions.updateStatusEntPaymentTaskByMdmId(mdmId, DctTaskStatuses.STATUS_REJECTED.getStatus());
                //создать новую запись в таблице taskStatusHistory
                entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

                connection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            // собираем объект для отправки в топик содержащее id задания, status=30, status_description если 30
            PayResultReward payResultReward =
                    createResultMessage(rewardId,
                            DctTaskStatuses.STATUS_REJECTED.getStatus(),
                            DctStatusDetails.MASTER_ACCOUNT_NOT_FOUND.getDescription()
                    );

            //Записать в топик rfrm_pay_result_reward сообщение
            sendResultToKafka(payResultReward);

        }

    }

    private void sendResultToKafka(PayResultReward payResultReward) {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        KafkaProducer<Object, PayResultReward> kafkaProducer = new KafkaProducer<>(properties);

        ProducerRecord<Object, PayResultReward> producerRecord = new ProducerRecord<>(topicResult, payResultReward);

        kafkaProducer.send(producerRecord);
        kafkaProducer.flush();
        kafkaProducer.close();
    }

    private PayResultReward createResultMessage(UUID rewardId, Integer status, String description) {

        return PayResultReward
                .builder()
                .rewardId(rewardId)
                .status(status)
                .statusDescription(description)
                .build();

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

    private AccountInfoRequest sendRequestListAccounts(List<String> str) {
        return AccountInfoRequest.builder().productTypes(str).build();
    }
    
}
