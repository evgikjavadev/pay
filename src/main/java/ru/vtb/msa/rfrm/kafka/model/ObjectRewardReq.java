package ru.vtb.msa.rfrm.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObjectRewardReq {
    private UUID id;
    private UUID requestId;
    private Integer recipientType;
    private Double money;

    //mdmId приходит из заголовка
    private String mdmId;
    private String productId;
}
