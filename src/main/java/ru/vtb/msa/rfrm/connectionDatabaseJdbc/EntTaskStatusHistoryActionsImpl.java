package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntTaskStatusHistory;

@Repository
@RequiredArgsConstructor
public class EntTaskStatusHistoryActionsImpl implements EntTaskStatusHistoryActions {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insertEntTaskStatusHistoryInDb(EntTaskStatusHistory entTaskStatusHistory) {
        String sql = "INSERT INTO ent_task_status_history " +
                "(reward_id, status_details_code, task_status, status_updated_at) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                entTaskStatusHistory.getRewardId(),
                entTaskStatusHistory.getStatusDetailsCode(),
                entTaskStatusHistory.getTaskStatus(),
                entTaskStatusHistory.getStatusUpdatedAt()
        );
    }

}
