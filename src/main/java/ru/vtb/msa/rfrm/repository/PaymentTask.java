package ru.vtb.msa.rfrm.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentTask {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "reward_id", nullable = false)
    private UUID rewardId;

    @Column(name = "request_id")
    private UUID requestId;

    @Column(name = "mdm_id")
    private String mdmId;

    @Column(name = "recipient_type")
    private Integer recipientType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "account_system")
    private String accountSystem;

    @Column(name = "account")
    private Integer account;

    @Column(name = "product_id")
    private String product_id;

    @Column(name = "response_sent")
    private Boolean responseSent;

    @OneToMany(mappedBy = "paymentTask")
    private List<TaskStatusHistory> taskStatusHistories;
}
