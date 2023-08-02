package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntTaskStatusHistory;

public interface EntTaskStatusHistoryActions {
    int insertEntTaskStatusHistoryInDb(EntTaskStatusHistory entTaskStatusHistory);
}
