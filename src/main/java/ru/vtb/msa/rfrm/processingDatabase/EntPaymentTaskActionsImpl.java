package ru.vtb.msa.rfrm.processingDatabase;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;

import java.util.List;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class EntPaymentTaskActionsImpl implements EntPaymentTaskActions {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void insertPaymentTaskInDB(EntPaymentTask entPaymentTask) {
        String sql = "INSERT INTO ent_payment_task " +
                "(reward_id, questionnaire_id, mdm_id, recipient_type, amount, status, created_at, account_system, account, source_qs, processed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                entPaymentTask.getRewardId(),
                entPaymentTask.getQuestionnaireId(),
                entPaymentTask.getMdmId(),
                entPaymentTask.getRecipientType(),
                entPaymentTask.getAmount(),
                entPaymentTask.getStatus(),
                entPaymentTask.getCreatedAt(),
                entPaymentTask.getAccountSystem(),
                entPaymentTask.getAccount(),
                entPaymentTask.getSourceQs(),
                entPaymentTask.getProcessed()
        );
    }

    @Override
    public List<EntPaymentTask> getPaymentTaskByMdmId(String mdmId) {
        String sql = "SELECT * FROM ent_payment_task WHERE mdm_id = ?";

        return jdbcTemplate.query(
                sql,
                new Object[] {mdmId},
                (rs, rowNum) -> {
                    EntPaymentTask task = new EntPaymentTask();
                    task.setRewardId(rs.getObject(1, UUID.class));
                    task.setMdmId(rs.getObject(3, String.class));
                    return task;
                });
    }

    @Override
    public void updateAccountNumber(String accountNumber, String accountSystem, String mdmId, Integer status) {
        String sql = "UPDATE ent_payment_task SET account = ?, account_system = ? WHERE mdm_id = ?";
        jdbcTemplate.update(sql, accountNumber, accountSystem, mdmId);
    }

    @Override
    public void updateStatusEntPaymentTaskByMdmId(String mdmId, Integer status) {
        String sql = "UPDATE ent_payment_task SET status = ? WHERE mdm_id = ?";
        jdbcTemplate.update(sql, status, mdmId);
    }

    @Override
    public void updateStatusEntPaymentTaskByRewardId(UUID rewardId, Integer status) {
        String sql = "UPDATE ent_payment_task SET status = ? WHERE reward_id = ?";
        jdbcTemplate.update(sql, status, rewardId);
    }

    @Override
    public List<EntPaymentTask> getPaymentTaskByRewardId(UUID rewardId) {
        String sql = "SELECT * FROM ent_payment_task WHERE reward_id = ?";
        return jdbcTemplate.query(
                sql,
                new Object[] {rewardId},
                (rs, rowNum) -> {
                    EntPaymentTask task = new EntPaymentTask();
                    task.setRewardId(rs.getObject(1, UUID.class));
                    return task;
                });
    }

    @Override
    public List<EntPaymentTask> getEntPaymentTaskByProcessed(Boolean b) {
        String sql = "SELECT * FROM ent_payment_task WHERE processed = ?";
        return jdbcTemplate.query(
                sql,
                new Object[] {b},
                (rs, rowNum) -> {
                    EntPaymentTask task = new EntPaymentTask();
                    task.setRewardId(rs.getObject(11, UUID.class));
                    return task;
                });
    }

    @Override
    public void updateProcessedBPaymentTaskByRewardId(UUID rewardId) {
        String sql = "UPDATE ent_payment_task SET processed = ? WHERE reward_id = ?";
        jdbcTemplate.update(sql, true, rewardId);
    }

    @Override
    public List<String> getEntPaymentTaskByStatus(Integer stat) {
        String sql = "SELECT * FROM ent_payment_task WHERE status = ?";
        return jdbcTemplate.query(
                sql,
                new Object[] {stat},
                (rs, rowNum) -> {
                    EntPaymentTask task = new EntPaymentTask();
                    task.setMdmId(rs.getObject("mdm_id", String.class));
                    return task.getMdmId();
                });
    }

}
