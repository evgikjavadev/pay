package ru.vtb.msa.rfrm.processingDatabase;

import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;

import java.util.List;

public interface EntPaymentTaskActions {
    void insertPaymentTaskInDB(EntPaymentTask entPaymentTask);

    List<EntPaymentTask> getPaymentTaskByMdmId(Long mdmId);

    void updateAccountNumber(String accountNumber, String accountSystem, Integer status, String accountBranch,  Long mdmId, Integer blocked);

    void updateStatusEntPaymentTaskByMdmId(Long mdmId, Integer status);

    void updateStatusEntPaymentTaskByRewardId(Long rewardId, Integer status);

    void updateProcessedBPaymentTaskByRewardId(Long rewardId);

}
