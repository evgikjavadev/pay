package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import ru.vtb.msa.rfrm.entitytodatabase.PayPaymentTask;

public interface PaymentTaskActions {
    int createPaymentTask(PayPaymentTask payPaymentTask);
}
