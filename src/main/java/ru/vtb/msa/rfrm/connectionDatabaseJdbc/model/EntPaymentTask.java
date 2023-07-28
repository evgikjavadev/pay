package ru.vtb.msa.rfrm.connectionDatabaseJdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntPaymentTask {

    /** ID задачи на оплату (генерируется ядром 2155 rfrm-core) */
    @Column(name = "reward_id")
    private UUID rewardId;

    /** ID заявки на участие в реферальной программе */
    //@NotNull
    @Column(name = "questionnaire_id")
    private UUID questionnaireId;

    /** ID получателя вознаграждения в MDM */
    //@NotNull
    @Column(name = "mdm_id")
    private String mdmId;

    /** Тип получателя вознаграждения */
    //@NotNull
    @Column(name = "recipient_type")
    private Integer recipientType;

    /** Сумма вознаграждения (определяется ядром 2155 rfrm-core) */
    //@NotNull
    @Column(name = "amount")
    private Double amount;

    /** Cтатус задания на оплату */
    //@NotNull
    @Column(name = "status")
    private Integer status;

    //@NotNull
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
    @Column(name = "source_qs")
    private String sourceQs;

    /** Отметка об отправке финального статуса */
    //@NotNull
    @Column(name = "response_sent")
    private Boolean responseSent;

    public LocalDateTime getCreatedAt() {
        return LocalDateTime.now();
    }
}
