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
    private UUID id;
    private UUID questionnaire_id;
    private Integer recipientType;
    private Double money;
    private String mdmId;   //todo   mdmId приходит из заголовка
    private String source_qs;
}
