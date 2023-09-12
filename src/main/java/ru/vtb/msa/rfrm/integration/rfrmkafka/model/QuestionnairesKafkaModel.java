package ru.vtb.msa.rfrm.integration.rfrmkafka.model;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnairesKafkaModel {
    private UUID rewardId;
    private Long mdmId;
    private UUID questionnaireId;
    private Integer recipientType;
    private Double amount;
    private String source_qs;

}
