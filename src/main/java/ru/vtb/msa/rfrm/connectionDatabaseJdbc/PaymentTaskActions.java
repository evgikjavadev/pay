package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import ru.vtb.msa.rfrm.modeldatabase.PayPaymentTask;

public interface PaymentTaskActions {
    int createPaymentTask(PayPaymentTask payPaymentTask);
}
