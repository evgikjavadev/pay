package ru.vtb.msa.rfrm.connectionDatabaseJdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntPayedList {

    @NotBlank
    @Column(name = "reward_id")
    private UUID rewardId;

    @NotBlank
    @Column(name = "created_at")
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @NotBlank
    @Column(name = "processed")
    private Boolean processed;
}
