package ru.vtb.msa.rfrm.processingDatabase;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class EntPaymentTaskActionsImpl implements EntPaymentTaskActions {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void insertPaymentTaskInDB(EntPaymentTask entPaymentTask) {
        String sql = "INSERT INTO ent_payment_task " +
                "(reward_id, questionnaire_id, mdm_id, recipient_type, amount, status, created_at, account_system, account, source_qs, processed, blocked, blocked_at, reward_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                entPaymentTask.getBlockedAt(),
                entPaymentTask.getRewardType()
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
                    task.setRewardId(rs.getObject(1, Long.class));
                    task.setMdmId(rs.getObject(3, Long.class));
                    return task;
                });
    }

    @Override
    public void updateAccountNumber(String accountNumber, String accountSystem,  Integer status, String accountBranch, Long mdmId, Integer blocked) {
        String sql = "UPDATE ent_payment_task SET account = ?, account_system = ?, status = ?, account_branch = ?, blocked = ? WHERE mdm_id = ?";
        jdbcTemplate.update(sql, accountNumber, accountSystem, status, accountBranch, blocked, mdmId);
    }

    @Override
    public void updateStatusEntPaymentTaskByMdmId(Long mdmId, Integer status) {
        String sql = "UPDATE ent_payment_task SET status = ? WHERE mdm_id = ?";
        jdbcTemplate.update(sql, status, mdmId);
    }

    @Override
    public void updateStatusEntPaymentTaskByRewardId(Long rewardId, Integer status) {
        String sql = "UPDATE ent_payment_task SET status = ? WHERE reward_id = ?";
        jdbcTemplate.update(sql, status, rewardId);
    }

    @Override
    public void updateProcessedBPaymentTaskByRewardId(Long rewardId) {
        String sql = "UPDATE ent_payment_task SET processed = ? WHERE reward_id = ?";
        jdbcTemplate.update(sql, true, rewardId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePaymentTaskByRewardIdSetStatusAndBlocked(Long rewardId) {
        String sql = "UPDATE ent_payment_task SET status = 30, blocked = 0 WHERE reward_id = ?";
        jdbcTemplate.update(sql, rewardId);
    }

}
