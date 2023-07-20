package ru.vtb.msa.rfrm.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObjectRewardReq {
    private String id;
    private String requestId;
    private Integer recipientType;
    private Double money;

    //mdmId приходит из заголовка
    private String mdmId;
    private String productId;
}
