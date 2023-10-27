package ru.vtb.msa.rfrm.processingDatabase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.processingDatabase.model.EntTaskStatusHistory;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EntTaskStatusHistoryActionsImpl implements EntTaskStatusHistoryActions {
    private final JdbcTemplate jdbcTemplate;
    private final ChangeStatusInfoLog changeStatusInfoLog;

    @Override
    public void insertEntTaskStatusHistoryInDb(EntTaskStatusHistory entTaskStatusHistory) {

        Integer taskStatusHistoryTaskStatus = entTaskStatusHistory.getTaskStatus();

        // получаем поле status_system_name из табл dct_task_statuses
        String statusSystemName = changeStatusInfoLog.getStatusSystemName(taskStatusHistoryTaskStatus);

        log.info("Значение task_status: {}, значение StatusSystemName: {}", taskStatusHistoryTaskStatus, statusSystemName);

        String sql = "INSERT INTO ent_task_status_history " +
                "(reward_id, status_details_code, task_status, status_updated_at) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                entTaskStatusHistory.getRewardId(),
                entTaskStatusHistory.getStatusDetailsCode(),
                entTaskStatusHistory.getTaskStatus(),
                entTaskStatusHistory.getStatusUpdatedAt(LocalDateTime.now())
        );
    }

}
