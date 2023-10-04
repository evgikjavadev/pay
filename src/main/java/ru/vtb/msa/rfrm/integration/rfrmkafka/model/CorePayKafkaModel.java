package ru.vtb.msa.rfrm.integration.rfrmkafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CorePayKafkaModel {
    @JsonProperty("reward_id")
    private Integer rewardId;
    @JsonProperty("mdm_id")
    private Long mdmId;
    @JsonProperty("questionnaire_id")
    private UUID questionnaireId;
    @JsonProperty("recipient_type_id")
    private Integer recipientType;
    @JsonProperty("amount_reward")
    private BigDecimal amount;
    @JsonProperty("source_qs")
    private String sourceQs;
    @JsonProperty("create_date")
    private LocalDateTime createDate;

}
