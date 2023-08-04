package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntPaymentTask;

import java.util.List;
import java.util.UUID;

public interface EntPaymentTaskActions {
    void insertPaymentTaskInDB(EntPaymentTask entPaymentTask);

    List<EntPaymentTask> getPaymentTaskByMdmId(String mdmId);

    void updateAccountNumber(String accountNumber, String accountSystem, String mdmId, Integer status);

    void updateStatus(String mdmId, Integer status);

    List<EntPaymentTask> getPaymentTaskByRewardId(UUID rewardId);

}
