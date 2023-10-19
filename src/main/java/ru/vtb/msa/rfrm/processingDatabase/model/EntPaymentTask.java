package ru.vtb.msa.rfrm.processingDatabase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("ent_payment_task")
public class EntPaymentTask {

    /** ID задачи на оплату (генерируется ядром 2155 rfrm-core) */
    @Id
    @Column("reward_id")
    private Integer rewardId;

    /** ID заявки на участие в реферальной программе */
    @Column("questionnaire_id")
    private UUID questionnaireId;

    /** ID получателя вознаграждения в MDM */
    @Column("mdm_id")
    private Long mdmId;

    /** Тип получателя вознаграждения */
    @Column("recipient_type")
    private Integer recipientType;

    /** Сумма вознаграждения (определяется ядром 2155 rfrm-core) */
    @Column("amount")
    private BigDecimal amount;

    /** Cтатус задания на оплату */
    @Column("status")
    private Integer status;

    @Column("created_at")
    private LocalDateTime createdAt;

    /** Система, в которой расположен счет клиента для оплаты вознаграждения */
    @Column("account_system")
    private String accountSystem;

    /** Счет клиента для оплаты вознаграждения */
    @Column("account")
    private String account;

    /** ID продукта, по которому должно быть выплачено вознаграждение за участие в реферальной программе */
    @Column("source_qs")
    private String sourceQs;

    @Column("processed")
    private Boolean processed;

    @Column("blocked")
    private Integer blocked;

    @Column("blocked_at")
    private LocalDateTime blockedAt;

    @Column("account_branch")
    private String accountBranch;

    /**
     Тип вознаграждения:
     1 - Выплата (рубли)
     2 - Выплата (бонусы) */
    @Column("reward_id")
    private Integer rewardType;

    public LocalDateTime getCreatedAt() {
        return LocalDateTime.now();
    }
}
