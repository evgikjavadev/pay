package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntTaskStatusHistory;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EntTaskStatusHistoryActionsImpl implements EntTaskStatusHistoryActions {
    private final JdbcTemplate jdbcTemplate;
    private final ChangeStatusInfoLog changeStatusInfoLog;

    @Override
    public void insertEntTaskStatusHistoryInDb(EntTaskStatusHistory entTaskStatusHistory) {


        changeStatusInfoLog.updateEnumStatuses(10);
        //log.info("Изменился StatusSystemName на {}, StatusDetailsDescription на {} ", );   //todo

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
