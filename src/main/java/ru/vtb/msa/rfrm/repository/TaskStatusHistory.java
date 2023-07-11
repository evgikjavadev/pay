package ru.vtb.msa.rfrm.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TaskStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "error_details")
    private Integer error_details;

    @Column(name = "reject_details")
    private Integer rejectDetails;

    @NotNull
    @JoinColumn
    @Column(name = "reward_id")
    private UUID rewardId;

    @NotNull
    @Column(name = "task_status")
    private Integer task_status;

    @NotNull
    @Column(name = "status_updated_at")
    private LocalDateTime statusUpdatedAt;

    @ManyToOne
    @JoinColumn(name = "payment_task_reward_id")
    private PaymentTask paymentTask;

}
