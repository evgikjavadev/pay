package ru.vtb.msa.rfrm.connectionDatabaseJdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntPaymentTask {

    /** ID задачи на оплату (генерируется ядром 2155 rfrm-core) */
    @NotBlank
    @Column(name = "reward_id")
    private UUID rewardId;

    /** ID заявки на участие в реферальной программе */
    @NotBlank
    @Column(name = "questionnaire_id")
    private UUID questionnaireId;

    /** ID получателя вознаграждения в MDM */
    @NotBlank
    @Column(name = "mdm_id")
    private String mdmId;

    /** Тип получателя вознаграждения */
    @NotBlank
    @Column(name = "recipient_type")
    private Integer recipientType;

    /** Сумма вознаграждения (определяется ядром 2155 rfrm-core) */
    @NotBlank
    @Column(name = "amount")
    private Double amount;

    /** Cтатус задания на оплату */
    @NotBlank
    @Column(name = "status")
    private Integer status;

    @NotBlank
    @Column(name = "created_at")
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    /** Система, в которой расположен счет клиента для оплаты вознаграждения */
    @Column(name = "account_system")
    private String accountSystem;

    /** Счет клиента для оплаты вознаграждения */
    @Column(name = "account")
    private String account;

    /** ID продукта, по которому должно быть выплачено вознаграждение за участие в реферальной программе */
    @NotBlank
    @Column(name = "source_qs")
    private String sourceQs;

    public LocalDateTime getCreatedAt() {
        return LocalDateTime.now();
    }
}
