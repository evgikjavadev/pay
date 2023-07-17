package ru.vtb.msa.rfrm.kafka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.core.serializer.DefaultSerializer;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectRewardReqDeser {
    private UUID id;
    private UUID requestId;
    private Integer recipientType;
    private Double money;

    //mdmId приходит из заголовка
    private String mdmId;
    private String productId;
}
