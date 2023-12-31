package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Account;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.PayCoreKafkaModel;
import ru.vtb.msa.rfrm.integration.rfrmkafka.processing.KafkaResultRewardProducer;
import ru.vtb.msa.rfrm.processingDatabase.EntPaymentTaskActions;
import ru.vtb.msa.rfrm.processingDatabase.EntTaskStatusHistoryActions;
import ru.vtb.msa.rfrm.processingDatabase.model.DctStatusDetails;
import ru.vtb.msa.rfrm.processingDatabase.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.processingDatabase.model.EntTaskStatusHistory;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class ProcessClientAccountsImpl implements ProcessClientAccounts {
    private final EntPaymentTaskActions entPaymentTaskActions;
    private final EntTaskStatusHistoryActions entTaskStatusHistoryActions;
    private final KafkaResultRewardProducer kafkaResultRewardProducer;

    @Override
    @Transactional
    public void processAccounts(Account accountNumberEntity, String result, Long mdmId, Long rewardId) {

        String masterAccountNumber = accountNumberEntity.getNumber();
        String accountSystem = accountNumberEntity.getEntitySubSystems();
        Boolean isArrested = accountNumberEntity.getIsArrested();
        String accountBranch = accountNumberEntity.getBranch();

        // обработка если мастер счет не найден
        if (masterAccountNumber.isEmpty() || masterAccountNumber.isBlank()) {
            // формируем объект для записи в табл. taskStatusHistory
            EntTaskStatusHistory entTaskStatusHistory =
                    createEntTaskStatusHistory(DctTaskStatuses.STATUS_REJECTED.getStatus(), null, rewardId);

            // создать новую запись в таблице taskStatusHistory
            entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

            // собираем и отправляем объект в топик rfrm_pay_result_reward содержащее id задания, status=30, status_description если 30, status_details_code=201
            PayCoreKafkaModel payCoreKafkaModel = createResultMessage(rewardId, DctTaskStatuses.STATUS_REJECTED.getStatus(),
                    DctStatusDetails.MASTER_ACCOUNT_NOT_FOUND.getDescription());

            kafkaResultRewardProducer.sendToResultReward(payCoreKafkaModel);

        }

        if ( !masterAccountNumber.equals("") && isArrested.equals(false)) {

            // Записать в БД для данного задания ent_payment_task.status=50 и blocked=0
            entPaymentTaskActions.updateAccountNumber(masterAccountNumber,
                    accountSystem, DctTaskStatuses.STATUS_READY_FOR_PAYMENT.getStatus(), accountBranch, mdmId, 0);

            // формируем объект для записи в табл. taskStatusHistory
            EntTaskStatusHistory entTaskStatusHistory =
                    createEntTaskStatusHistory(DctTaskStatuses.STATUS_READY_FOR_PAYMENT.getStatus(), null, rewardId);

            // создать новую запись в таблице taskStatusHistory
            entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

        }

        if (isArrested.equals(true)) {

            //обновляем в БД для данного задания paymentTask.status=30
            entPaymentTaskActions.updateStatusEntPaymentTaskByRewardId(rewardId, DctTaskStatuses.STATUS_REJECTED.getStatus());

            // формируем данные для сохранения в БД ent_task_status_history
            EntTaskStatusHistory entTaskStatusHistory = createEntTaskStatusHistory(DctTaskStatuses.STATUS_REJECTED.getStatus(),
                    DctStatusDetails.MASTER_ACCOUNT_ARRESTED.getStatusDetailsCode(), rewardId);

            // создать новую запись в таблице ent_task_status_history
            entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

            // собираем и отправляем объект в топик rfrm_pay_result_reward содержащее id задания, status=30, status_description если 30, status_details_code=202
            PayCoreKafkaModel payCoreKafkaModel = createResultMessage(rewardId, DctTaskStatuses.STATUS_REJECTED.getStatus(),
                    DctStatusDetails.MASTER_ACCOUNT_ARRESTED.getDescription());

            kafkaResultRewardProducer.sendToResultReward(payCoreKafkaModel);
        }

        if (masterAccountNumber.isEmpty() && result.equalsIgnoreCase("ok")) {

            //  создать новую запись в таблице taskStatusHistory с taskStatusHistory.status_details=201
            EntTaskStatusHistory entTaskStatusHistory =
                    createEntTaskStatusHistory(DctTaskStatuses.STATUS_REJECTED.getStatus(),
                            DctStatusDetails.MASTER_ACCOUNT_NOT_FOUND.getStatusDetailsCode(), rewardId);

            // собираем объект для отправки в топик rfrm_pay_result_reward содержащее id задания, status=30, status_details_code=201
            PayCoreKafkaModel payCoreKafkaModel1 =
                    createResultMessage(rewardId, DctTaskStatuses.STATUS_REJECTED.getStatus(),
                            DctStatusDetails.MASTER_ACCOUNT_ARRESTED.getDescription());

            // Записать в БД для данного задания paymentTask.status=30,
            entPaymentTaskActions.updateStatusEntPaymentTaskByRewardId(rewardId, DctTaskStatuses.STATUS_REJECTED.getStatus());

            //создать новую запись в таблице taskStatusHistory
            entTaskStatusHistoryActions.insertEntTaskStatusHistoryInDb(entTaskStatusHistory);

            // Записать в топик rfrm_pay_result_reward сообщение, содержащее id задания и status=30, status_details_code=201
            kafkaResultRewardProducer.sendToResultReward(payCoreKafkaModel1);

        }

        //todo   resolve if only result = ok

    }

    private PayCoreKafkaModel createResultMessage(Long rewardId, Integer status, String description) {

        return PayCoreKafkaModel
                .builder()
                .rewardId(rewardId)
                .status(status)
                .statusDescription(description)
                .build();
    }

    private EntTaskStatusHistory createEntTaskStatusHistory(Integer taskStatus, Integer statusDetailsCode, Long rewardId) {
        return EntTaskStatusHistory
                .builder()
                .rewardId(rewardId)
                .statusDetailsCode(statusDetailsCode)
                .taskStatus(taskStatus)
                .statusUpdatedAt(LocalDateTime.now())
                .build();
    }
}
