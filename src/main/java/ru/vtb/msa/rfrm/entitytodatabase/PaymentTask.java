package ru.vtb.msa.rfrm.entitytodatabase;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reward_id")
    private UUID rewardId;

//    @Id
//    @GeneratedValue(generator="reward_id")
//    @GenericGenerator(name="reward_id", strategy = "uuid")
//    private UUID rewardId;



    //@NotNull
    @Column(name = "request_id")
    private String requestId;

    //@NotNull
    @Column(name = "mdm_id")
    private String mdmId;

    //@NotNull
    @Column(name = "recipient_type")
    private Integer recipientType;

    //@NotNull
    @Column(name = "amount")
    private Double amount;

    //@NotNull
    @Column(name = "status")
    private Integer status;

    //@NotNull
    @Column(name = "created_at")
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "account_system")
    private String accountSystem;

    @Column(name = "account")
    private Integer account;

    //@NotNull
    @Column(name = "product_id")
    private String product_id;

    //@NotNull
    @Column(name = "response_sent")
    private Boolean responseSent;

//    @OneToMany(mappedBy = "paymentTask")
//    private List<TaskStatusHistory> taskStatusHistories;           //todo

//    @ManyToOne
//    @JoinColumn(name = "task_statuses_status")
//    private TaskStatuses taskStatuses;

}
