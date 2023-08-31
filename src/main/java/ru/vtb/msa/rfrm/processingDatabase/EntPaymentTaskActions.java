package ru.vtb.msa.rfrm.processingDatabase;

import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;

import java.util.List;
import java.util.UUID;

public interface EntPaymentTaskActions {
    void insertPaymentTaskInDB(EntPaymentTask entPaymentTask);

    List<EntPaymentTask> getPaymentTaskByMdmId(String mdmId);

    void updateAccountNumber(String accountNumber, String accountSystem, String mdmId, Integer status);

    void updateStatusEntPaymentTaskByMdmId(String mdmId, Integer status);

    void updateStatusEntPaymentTaskByRewardId(UUID rewardId, Integer status);

    List<EntPaymentTask> getPaymentTaskByRewardId(UUID rewardId);

    List<UUID> getEntPaymentTaskByProcessed(Boolean b);
    void updateProcessedBPaymentTaskByRewardId(UUID rewardId);
    List<String> getEntPaymentTaskByStatus(Integer status);
}
