package ru.vtb.msa.rfrm.processingDatabase.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("ent_task_status_history")
public class EntTaskStatusHistory {

    /** id события (ключ) */
    @Id
    @Column("status_history_id")
    private BigInteger statusHistoryId;

    /** id задания, по которому изменился статус соответствует reward_id */
    @Column("reward_id")
    private Integer rewardId;

    /** Код комментария к статусу заявки */
    @Column("status_details_code")
    private Integer statusDetailsCode;

    /** Статус, присвоенный заданию */
    @Column("task_status")
    private Integer taskStatus;

    /** status_updated_at */
    @Column("status_updated_at")
    private LocalDateTime statusUpdatedAt;

}
