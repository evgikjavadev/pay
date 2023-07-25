package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.modeldatabase.PayPaymentTask;


@Repository
public class PaymentTaskActionsDbImpl implements PaymentTaskActionsDb {

    private final JdbcTemplate jdbcTemplate;

    public PaymentTaskActionsDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPaymentTaskInDB(PayPaymentTask payPaymentTask) {
        String sql = "INSERT INTO pay_payment_task " +
                "(reward_id, questionnaire_id, mdm_id, recipient_type, amount, status, created_at, account_system, account, source_qs, response_sent) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                payPaymentTask.getRewardId(),
                payPaymentTask.getQuestionnaireId(),
                payPaymentTask.getMdmId(),
                payPaymentTask.getRecipientType(),
                payPaymentTask.getAmount(),
                payPaymentTask.getStatus(),
                payPaymentTask.getCreatedAt(),
                payPaymentTask.getAccountSystem(),
                payPaymentTask.getAccount(),
                payPaymentTask.getSourceQs(),
                payPaymentTask.getResponseSent()

        );
    }

    @Override
    public PayPaymentTask getPaymentTaskByMdmId(String mdmId) {
        String sql = "SELECT * FROM pay_payment_task WHERE mdm_id = ?";

        RowMapper<PayPaymentTask> rowMapper = new BeanPropertyRowMapper<>(PayPaymentTask.class);

        PayPaymentTask payPaymentTask = jdbcTemplate.queryForObject(sql, rowMapper, mdmId);
        return payPaymentTask;

    }

    @Override
    public void updateAccountNumber(Integer accountNumber, String accountSystem) {

        String sql = "UPDATE pay_payment_task SET account = ?, account_system = ? WHERE mdm_id = ?";
        jdbcTemplate.update(sql, accountNumber, accountSystem);

    }

}
