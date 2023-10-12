package ru.vtb.msa.rfrm.processingDatabase;

import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;

import java.util.List;

public interface EntPaymentTaskActions {
    void insertPaymentTaskInDB(EntPaymentTask entPaymentTask);

    List<EntPaymentTask> getPaymentTaskByMdmId(Long mdmId);

    void updateAccountNumber(String accountNumber, String accountSystem, Long mdmId, Integer status, String accountBranch);

    void updateStatusEntPaymentTaskByMdmId(Long mdmId, Integer status);

    void updateStatusEntPaymentTaskByRewardId(Integer rewardId, Integer status);

    void updateProcessedBPaymentTaskByRewardId(Integer rewardId);

}
