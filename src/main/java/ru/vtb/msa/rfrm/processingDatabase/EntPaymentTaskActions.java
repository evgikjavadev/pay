package ru.vtb.msa.rfrm.processingDatabase;

import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;

import java.util.List;
import java.util.UUID;

public interface EntPaymentTaskActions {
    void insertPaymentTaskInDB(EntPaymentTask entPaymentTask);

    List<EntPaymentTask> getPaymentTaskByMdmId(Long mdmId);

    void updateAccountNumber(String accountNumber, String accountSystem, Long mdmId, Integer status);

    void updateStatusEntPaymentTaskByMdmId(Long mdmId, Integer status);

    void updateStatusEntPaymentTaskByRewardId(UUID rewardId, Integer status);

    //List<EntPaymentTask> getPaymentTaskByRewardId(UUID rewardId);

    List<EntPaymentTask> getEntPaymentTaskByProcessed(Boolean b);

    void updateProcessedBPaymentTaskByRewardId(UUID rewardId);

    //List<Long> getEntPaymentTaskByStatus(Integer status);

    //List<EntPaymentTask> getRewardIdsByProcessAndBlocked(Integer size);

}
