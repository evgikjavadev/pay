package ru.vtb.msa.rfrm.integration.rfrmcore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/** Объект который приходит из топика RewardReq */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObjectRewardReq {
    private UUID rewardId;
    private UUID questionnaireId;
    private Integer recipientType;
    private Double amount;
    private String mdmId;
    private String source_qs;

}
