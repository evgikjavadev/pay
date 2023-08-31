package ru.vtb.msa.rfrm.integration.internalkafka.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InternalMessageModel {
    private String functionName;
    private String status;
    private LocalDateTime timeStamp;

}
