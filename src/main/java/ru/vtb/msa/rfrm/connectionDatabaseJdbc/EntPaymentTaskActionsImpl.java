package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.connectionDatabaseJdbc.model.EntPaymentTask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class EntPaymentTaskActionsImpl implements EntPaymentTaskActions {

    private final JdbcTemplate jdbcTemplate;

    private final HikariDataSource hikariDataSource;


    @Override
    public int insertPaymentTaskInDB(EntPaymentTask entPaymentTask) {
        String sql = "INSERT INTO ent_payment_task " +
                "(reward_id, questionnaire_id, mdm_id, recipient_type, amount, status, created_at, account_system, account, source_qs) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                entPaymentTask.getRewardId(),
                entPaymentTask.getQuestionnaireId(),
                entPaymentTask.getMdmId(),
                entPaymentTask.getRecipientType(),
                entPaymentTask.getAmount(),
                entPaymentTask.getStatus(),
                entPaymentTask.getCreatedAt(),
                entPaymentTask.getAccountSystem(),
                entPaymentTask.getAccount(),
                entPaymentTask.getSourceQs()
        );
    }

    @Override
    public List<EntPaymentTask> getPaymentTaskByMdmId(String mdmId) {
        String sql = "SELECT * FROM ent_payment_task WHERE mdm_id = ?";
        List<EntPaymentTask> listPaymentTasks = jdbcTemplate.query(
                sql,
                new Object[] {mdmId},
                new RowMapper<EntPaymentTask>() {
                    public EntPaymentTask mapRow(ResultSet rs, int rowNum) throws SQLException {
                        EntPaymentTask task = new EntPaymentTask();
                        task.setMdmId(rs.getObject(3, String.class));
                        return task;
                    }
                });
        return listPaymentTasks;
    }

    @Override
    public void updateAccountNumber(String accountNumber, String accountSystem, String mdmId, Integer status) {
        String sql = "UPDATE ent_payment_task SET account = ?, account_system = ? WHERE mdm_id = ?";
        jdbcTemplate.update(sql, accountNumber, accountSystem, mdmId);
    }

    @Override
    public void updateStatus(String mdmId, Integer status) {
        String sql = "UPDATE ent_payment_task SET status = ? WHERE mdm_id = ?";
        jdbcTemplate.update(sql, status, mdmId);
    }

}
