package ru.vtb.msa.rfrm.connectionDatabaseJdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vtb.msa.rfrm.modeldatabase.PayPaymentTask;


@Repository
public class PaymentTaskActionsImpl implements PaymentTaskActions {

    private final JdbcTemplate jdbcTemplate;

    public PaymentTaskActionsImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createPaymentTask(PayPaymentTask payPaymentTask) {
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
}
