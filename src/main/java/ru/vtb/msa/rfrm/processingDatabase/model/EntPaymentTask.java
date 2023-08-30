package ru.vtb.msa.rfrm.processingDatabase.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("ent_payment_task")
public class EntPaymentTask {

    /** ID задачи на оплату (генерируется ядром 2155 rfrm-core) */
    @NotBlank
    @Column("reward_id")
    private UUID rewardId;

    /** ID заявки на участие в реферальной программе */
    @NotBlank
    @Column("questionnaire_id")
    private UUID questionnaireId;

    /** ID получателя вознаграждения в MDM */
    @NotBlank
    @Column("mdm_id")
    private String mdmId;

    /** Тип получателя вознаграждения */
    @NotBlank
    @Column("recipient_type")
    private Integer recipientType;

    /** Сумма вознаграждения (определяется ядром 2155 rfrm-core) */
    @NotBlank
    @Column("amount")
    private Double amount;

    /** Cтатус задания на оплату */
    @NotBlank
    @Column("status")
    private Integer status;

    @NotBlank
    @Column("created_at")
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    /** Система, в которой расположен счет клиента для оплаты вознаграждения */
    @Column("account_system")
    private String accountSystem;

    /** Счет клиента для оплаты вознаграждения */
    @Column("account")
    private String account;

    /** ID продукта, по которому должно быть выплачено вознаграждение за участие в реферальной программе */
    @NotBlank
    @Column("source_qs")
    private String sourceQs;

    @Column("processed")
    private Boolean processed;

    public LocalDateTime getCreatedAt() {
        return LocalDateTime.now();
    }
}
