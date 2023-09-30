package ru.vtb.msa.rfrm.processingDatabase;

import org.apache.kafka.common.metrics.internals.IntGaugeSuite;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;

import java.util.List;

public interface EntPaymentTaskActions {
    void insertPaymentTaskInDB(EntPaymentTask entPaymentTask);

    List<EntPaymentTask> getPaymentTaskByMdmId(Long mdmId);

    void updateAccountNumber(String accountNumber, String accountSystem, Long mdmId, Integer status);

    void updateStatusEntPaymentTaskByMdmId(Long mdmId, Integer status);

    void updateStatusEntPaymentTaskByRewardId(Integer rewardId, Integer status);

    void updateProcessedBPaymentTaskByRewardId(Integer rewardId);

}
