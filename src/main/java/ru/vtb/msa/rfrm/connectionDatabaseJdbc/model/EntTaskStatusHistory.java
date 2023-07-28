package ru.vtb.msa.rfrm.connectionDatabaseJdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntTaskStatusHistory {

    /** id события (ключ) */
    @Id
    @NotBlank
    @Column(name = "status_history_id")
    private BigInteger statusHistoryId;

    /** id задания, по которому изменился статус соответствует reward_id */
    @NotBlank
    @Column(name = "task_id")
    private UUID taskId;

    /** Код комментария к статусу заявки */
    @Column(name = "status_details_code")
    private Integer statusDetailsCode;

    /** Статус, присвоенный заданию */
    @NotBlank
    @Column(name = "task_status")
    private Integer taskStatus;

    /** status_updated_at */
    @NotBlank
    @Column(name = "status_updated_at")
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime statusUpdatedAt;

}
