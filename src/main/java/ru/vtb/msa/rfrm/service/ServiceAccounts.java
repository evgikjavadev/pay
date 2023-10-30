package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.integration.kafkainternal.KafkaInternalProducer;
import ru.vtb.msa.rfrm.integration.kafkainternal.model.InternalMessageModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreKafkaModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.processing.KafkaResultRewardProducer;
import ru.vtb.msa.rfrm.processingDatabase.EntTaskStatusHistoryActions;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.processingDatabase.batch.ActionEntPaymentTaskRepo;
import ru.vtb.msa.rfrm.processingDatabase.model.DctStatusDetails;
import ru.vtb.msa.rfrm.processingDatabase.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.processingDatabase.model.EntTaskStatusHistory;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.integration.HttpStatusException;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Account;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Response;
import ru.vtb.msa.rfrm.integration.personaccounts.client.PersonClientAccounts;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.request.AccountInfoRequest;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceAccounts implements ServiceAccountsInterface {
    private final PersonClientAccounts personClientAccounts;
    private final EntPaymentTaskActions entPaymentTaskActions;
    private final EntTaskStatusHistoryActions entTaskStatusHistoryActions;
    private final ProcessClientAccounts processClientAccounts;
    private final KafkaResultRewardProducer kafkaResultRewardProducer;

    @Override
    public void getClientAccounts(Long mdmIdFromKafka, Long rewardId) {

        try {
            // получаем весь объект с данными счета клиента из 1503
            Response<?> account = personClientAccounts
                    .getPersonAccounts(mdmIdFromKafka, sendRequestListAccounts(Collections.singletonList("ACCOUNT")));

            if (!account.getBody().getAccounts().isEmpty()) {
                getAndPassParameters(account, mdmIdFromKafka, rewardId);
            }

        } catch (HttpStatusException e) {
            HttpStatus status = e.getStatus();
            handleResponseHttpStatuses(status, mdmIdFromKafka);
        } catch (Exception ex ) {
            // если здесь - то ответ от прод профиля не валидный и соответственно счет не найден
            if (ex.getCause().toString().contains("JsonEOFException")) {

                log.info("JSON is not valid and Account is not found! " + ex.getCause().toString());

                //handlingJsonException(rewardId);

            } else {
                log.info("Valid account is not found! for rewardId {}", rewardId);
            }
        }

    }

    private void handlingJsonException(Long rewardId) {

        EntTaskStatusHistory entTaskStatusHistory = EntTaskStatusHistory
                .builder()
                .rewardId(rewardId)
                .taskStatus(DctTaskStatuses.STATUS_REJECTED.getStatus())
                .statusDetailsCode(DctStatusDetails.MASTER_ACCOUNT_NOT_FOUND.getStatusDetailsCode())
                .statusUpdatedAt(LocalDateTime.now())
                .build();

        entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

        PayCoreKafkaModel payCoreKafkaModel = PayCoreKafkaModel
                .builder()
                .rewardId(rewardId)
                .status(DctTaskStatuses.STATUS_REJECTED.getStatus())
                .statusDescription(DctStatusDetails.MASTER_ACCOUNT_NOT_FOUND.getDescription())
                .build();

        kafkaResultRewardProducer.sendToResultReward(payCoreKafkaModel);

    }

    private void handleResponseHttpStatuses(HttpStatus status, Long mdmIdFromKafka) {

        // найдем в табл. ent_payment_task rewardId по mdmId
        List<EntPaymentTask> paymentTaskByMdmId = entPaymentTaskActions
                .getPaymentTaskByMdmId(mdmIdFromKafka);

        if (status.value() == 404) {

            for (EntPaymentTask elem: paymentTaskByMdmId) {

                // формируем объект для табл. taskStatusHistory
                EntTaskStatusHistory entTaskStatusHistory = EntTaskStatusHistory
                        .builder()
                        .rewardId(elem.getRewardId())
                        .statusDetailsCode(DctStatusDetails.CLIENT_NOT_FOUND_IN_MDM.getStatusDetailsCode())
                        .taskStatus(DctTaskStatuses.STATUS_MANUAL_PROCESSING.getStatus())
                        .statusUpdatedAt(LocalDateTime.now())
                        .build();

                // обновляем табл. ent_payment_task
                entPaymentTaskActions.updateStatusEntPaymentTaskByRewardId(elem.getRewardId(), DctTaskStatuses.STATUS_MANUAL_PROCESSING.getStatus());

                // создать новую запись в таблице taskStatusHistory с taskStatusHistory.status_details_code=101
                entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

            }

            // отправить сообщение об ошибке, требующей ручного разбора, в мониторинг
            //todo  отправить сообщение об ошибке, требующей ручного разбора, в мониторинг

        }

        if (status.value() == 400 || status.value() == 500) {

            List<Long> rewardIdList = new ArrayList<>();

            for (EntPaymentTask elem: paymentTaskByMdmId) {
                rewardIdList.add(elem.getRewardId());
            }

            // присвоить заданию blocked=0
            //entPaymentTaskRepository.updateBlockedByRewardId(0, Timestamp.valueOf(LocalDateTime.now()), rewardIdList);   //todo

        }

    }

    private void getAndPassParameters(Response<?> personAccounts, Long mdmId, Long rewardId) {
        log.info("Start process handle personAccounts: {}, mdmId: {}, rewardId: {}", personAccounts, mdmId, rewardId);

        Map.Entry<String, Account> masterAccount = null;

        // получаем значение result в ответе от 1503
        String result = personAccounts.getBody().getResult();

        ArrayList<Map.Entry<String, Account>> entries = new ArrayList<>(personAccounts.getBody().getAccounts().entrySet());

        try {
            masterAccount = entries.stream()
                    .filter(a -> (a.getValue().getEntityType().equalsIgnoreCase("MASTER_ACCOUNT")
                            && a.getValue().getBalance().getCurrency().equalsIgnoreCase("RUB")))
                    .findFirst()
                    .orElseThrow();

            processClientAccounts.processAccounts(masterAccount.getValue(), result, mdmId, rewardId);

        } catch (Exception e) {
            log.info("MASTER_ACCOUNT with RUB is not found!");
            assert result != null;
            if (result.equalsIgnoreCase("ok")) {
                // Записать в БД для данного задания paymentTask.status=30 и blocked=0
                entPaymentTaskActions.updatePaymentTaskByRewardIdSetStatusAndBlocked(rewardId);

                // соберем объект для табл status history
                EntTaskStatusHistory entTaskStatusHistory = EntTaskStatusHistory
                        .builder()
                        .rewardId(rewardId)
                        .statusDetailsCode(DctStatusDetails.MASTER_ACCOUNT_NOT_FOUND.getStatusDetailsCode())
                        .taskStatus(DctTaskStatuses.STATUS_REJECTED.getStatus())
                        .statusUpdatedAt(LocalDateTime.now())
                        .build();

                // создать новую запись в таблице ent_task_status_history с ent_task_status_history.status_details=201
                entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

                // соберем объект для записи в топик
                PayCoreKafkaModel payCoreKafkaModel = PayCoreKafkaModel
                        .builder()
                        .rewardId(rewardId)
                        .status(DctTaskStatuses.STATUS_REJECTED.getStatus())
                        .statusDescription(DctStatusDetails.MASTER_ACCOUNT_NOT_FOUND.getDescription())
                        .build();

                // Записать в топик rfrm_pay_result_reward сообщение, содержащее id задания и status=30, status_details_code=201
                kafkaResultRewardProducer.sendToResultReward(payCoreKafkaModel);

            }
        }

        log.info("Finish process handle personAccounts: {}, mdmId: {}, rewardId: {}", personAccounts, mdmId, rewardId);
    }

    private AccountInfoRequest sendRequestListAccounts(List<String> str) {
        return AccountInfoRequest.builder().productTypes(str).build();
    }
    
}
