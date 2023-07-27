package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntTaskStatusHistory;

public interface PayTaskStatusHistoryActions {
    int insertEntTaskStatusHistoryInDb(EntTaskStatusHistory entTaskStatusHistory);
}
