package ru.vtb.msa.rfrm.integration.rfrmkafka.model;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PayCoreLinkModel {
    private UUID rewardId;
    private Integer status;
    private String statusDescription;
}
