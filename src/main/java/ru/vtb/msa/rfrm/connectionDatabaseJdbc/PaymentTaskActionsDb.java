package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import ru.vtb.msa.rfrm.modeldatabase.PayPaymentTask;

public interface PaymentTaskActionsDb {
    int insertPaymentTaskInDB(PayPaymentTask payPaymentTask);

    PayPaymentTask getPaymentTaskByMdmId(String mdmId);

    void updateAccountNumber(Integer accountNumber, String accountSystem);


}
