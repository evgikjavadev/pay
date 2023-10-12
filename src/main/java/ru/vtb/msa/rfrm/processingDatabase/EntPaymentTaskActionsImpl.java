package ru.vtb.msa.rfrm.processingDatabase;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class EntPaymentTaskActionsImpl implements EntPaymentTaskActions {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void insertPaymentTaskInDB(EntPaymentTask entPaymentTask) {
        String sql = "INSERT INTO ent_payment_task " +
                "(reward_id, questionnaire_id, mdm_id, recipient_type, amount, status, created_at, account_system, account, source_qs, processed, blocked, blocked_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                entPaymentTask.getProcessed(),
                entPaymentTask.getBlocked(),
                entPaymentTask.getBlockedAt()
        );
    }

    @Override
    public List<EntPaymentTask> getPaymentTaskByMdmId(Long mdmId) {
        String sql = "SELECT * FROM ent_payment_task WHERE mdm_id = ?";

        return jdbcTemplate.query(
                sql,
                new Object[] {mdmId},
                (rs, rowNum) -> {
                    EntPaymentTask task = new EntPaymentTask();
                    task.setRewardId(rs.getObject(1, Integer.class));
                    task.setMdmId(rs.getObject(3, Long.class));
                    return task;
                });
    }

    @Override
    public void updateAccountNumber(String accountNumber, String accountSystem, Long mdmId, Integer status, String accountBranch) {
        String sql = "UPDATE ent_payment_task SET account = ?, account_system = ?, account_branch = ? WHERE mdm_id = ?";
        jdbcTemplate.update(sql, accountNumber, accountSystem, accountBranch, mdmId);
    }

    @Override
    public void updateStatusEntPaymentTaskByMdmId(Long mdmId, Integer status) {
        String sql = "UPDATE ent_payment_task SET status = ? WHERE mdm_id = ?";
        jdbcTemplate.update(sql, status, mdmId);
    }

    @Override
    public void updateStatusEntPaymentTaskByRewardId(Integer rewardId, Integer status) {
        String sql = "UPDATE ent_payment_task SET status = ? WHERE reward_id = ?";
        jdbcTemplate.update(sql, status, rewardId);
    }

    @Override
    public void updateProcessedBPaymentTaskByRewardId(Integer rewardId) {
        String sql = "UPDATE ent_payment_task SET processed = ? WHERE reward_id = ?";
        jdbcTemplate.update(sql, true, rewardId);
    }

}
