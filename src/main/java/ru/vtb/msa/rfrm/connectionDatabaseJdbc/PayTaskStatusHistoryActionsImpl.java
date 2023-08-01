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
                "(rewardId, status_details_code, task_status, status_updated_at) " +
                "VALUES (?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                entTaskStatusHistory.getRewardId(),
                entTaskStatusHistory.getStatusDetailsCode(),
                entTaskStatusHistory.getTaskStatus(),
                entTaskStatusHistory.getStatusUpdatedAt()
        );
    }

}
