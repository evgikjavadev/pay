package ru.vtb.msa.rfrm.integration.rfrmkafka.model;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PayCoreLinkModel {
    private Integer rewardId;
    private Integer status;
    private String statusDescription;
}
