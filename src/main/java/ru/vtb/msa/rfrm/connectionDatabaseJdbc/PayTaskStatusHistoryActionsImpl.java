package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntTaskStatusHistory;

@Repository
@RequiredArgsConstructor
public class PayTaskStatusHistoryActionsImpl implements PayTaskStatusHistoryActions {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int insertEntTaskStatusHistoryInDb(EntTaskStatusHistory entTaskStatusHistory) {
        String sql = "INSERT INTO ent_task_status_history " +
                "(status_history_id, status_details_code, task_id, task_status, status_updated_at) " +
                "VALUES (?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                entTaskStatusHistory.getStatusHistoryId(),
                entTaskStatusHistory.getStatusDetailsCode(),
                entTaskStatusHistory.getTaskId(),
                entTaskStatusHistory.getTaskStatus(),
                entTaskStatusHistory.getStatusUpdatedAt()
        );
    }

}
