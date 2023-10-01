package ru.vtb.msa.rfrm.integration.internalkafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InternalMessageModel {
    @JsonProperty("function_name")
    private String functionName;
    @JsonProperty("status")
    private String status;
    @JsonProperty("timestamp")
    private LocalDateTime timeStamp;

}
