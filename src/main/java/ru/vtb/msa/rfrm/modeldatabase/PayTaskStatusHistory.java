package ru.vtb.msa.rfrm.modeldatabase;

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
public class PayTaskStatusHistory {

    /** id события (ключ) */
    private UUID id;

    /** id задания, по которому изменился статус */
    @Column(name = "task_id")
    private UUID taskId;

    /** Код комментария к статусу заявки */
    @Column(name = "status_details")
    private Integer statusDetails;

    /** Статус, присвоенный заданию */
    //@NotNull
    @Column(name = "task_status")
    private Integer taskStatus;

    /** status_updated_at */
    //@NotNull
    @Column(name = "status_updated_at")
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime statusUpdatedAt;

}
