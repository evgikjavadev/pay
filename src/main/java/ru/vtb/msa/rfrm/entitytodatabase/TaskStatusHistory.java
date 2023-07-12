package ru.vtb.msa.rfrm.entitytodatabase;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    private Integer errorDetails;

    @Column(name = "reject_details")
    private Integer rejectDetails;

//    @NotNull
//    @JoinColumn
//    @Column(name = "reward_id")
//    private UUID rewardId;

    //@NotNull
    @Column(name = "task_status")
    private Integer taskStatus;

    //@NotNull
    @Column(name = "status_updated_at")
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime statusUpdatedAt;

//    @ManyToOne
//    @JoinColumn(name = "payment_task_reward_id")      //todo
//    private PaymentTask paymentTask;

}
