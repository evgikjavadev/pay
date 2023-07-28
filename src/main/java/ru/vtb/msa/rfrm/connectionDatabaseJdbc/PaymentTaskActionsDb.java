package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntPaymentTask;

import java.util.List;

public interface PaymentTaskActionsDb {
    int insertPaymentTaskInDB(EntPaymentTask entPaymentTask);

    List<EntPaymentTask> getPaymentTaskByMdmId(String mdmId);

    void updateAccountNumber(Integer accountNumber, String accountSystem, String mdmId, Integer status);

    void updateStatus(String mdmId, Integer status);


}
