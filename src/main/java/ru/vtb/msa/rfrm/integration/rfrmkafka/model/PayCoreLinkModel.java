package ru.vtb.msa.rfrm.integration.rfrmkafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PayCoreLinkModel {
    @JsonProperty("reward_id")
    private Integer rewardId;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("status_description")
    private String statusDescription;
}
