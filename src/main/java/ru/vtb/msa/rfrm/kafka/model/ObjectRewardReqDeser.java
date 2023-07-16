package ru.vtb.msa.rfrm.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.core.serializer.DefaultSerializer;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectRewardReqDeser {
    private UUID id;
    private UUID requestId;
    private Integer recipientType;
    private Double money;

    //mdmId приходит из заголовка
    //private Integer mdmId;
    private String productId;
}
