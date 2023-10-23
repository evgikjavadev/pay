package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.processingDatabase.EntTaskStatusHistoryActions;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
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

    @SneakyThrows
    @Override
    public void getClientAccounts(Long mdmIdFromKafka, Long rewardId) {

        try {
            // получаем весь объект с данными счета клиента из 1503
            Response<?> personAccounts = personClientAccounts
                    .getPersonAccounts(mdmIdFromKafka, sendRequestListAccounts(Collections.singletonList("ACCOUNT")));

            getAndPassParameters(personAccounts, mdmIdFromKafka, rewardId);

        } catch (HttpStatusException e) {
            HttpStatus status = e.getStatus();
            handleResponseHttpStatuses(status, mdmIdFromKafka);
        }

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
        //personAccounts.getBody().getAccounts().stream().filter(a -> a.)

        //new ArrayList<>(personAccounts.getBody().getAccounts());
        //Account masterAccount = findMasterAccountRub(accountList);

        // получаем значение result в ответе от 1503
        String result = personAccounts.getBody().getResult();

        // получаем mdmId из заголовков ответа от 1503
        //Long mdmId = getMdmId(personAccounts);

        //processClientAccounts.processAccounts(masterAccount, result, mdmId, rewardId);
        log.info("Finish process handle personAccounts: {}, mdmId: {}, rewardId: {}", personAccounts, mdmId, rewardId);
    }

    private AccountInfoRequest sendRequestListAccounts(List<String> str) {
        return AccountInfoRequest.builder().productTypes(str).build();
    }

    private Account findMasterAccountRub(List<Account> accountList) {

        return accountList.stream()
                .filter(a -> a.getEntityType().equals("MASTER_ACCOUNT"))
                .filter(b -> b.getBalance().getCurrency().equals("RUB"))
                .findFirst()
                .orElseThrow();
    }

//    private static Long getMdmId(Response<?> personAccounts) {
//
//        return Long.valueOf(personAccounts
//                .getHeaders()
//                .entrySet()
//                .stream()
//                .filter(a -> a.getKey().equals("X-Mdm-Id"))
//                .findFirst()
//                .orElseThrow()
//                .getValue()
//                .get(0));
//    }
    
}
