package ru.vtb.msa.rfrm.integration.internalkafka;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class InternalMessageModel {
    private String functionName;
    private String status;
    private LocalDateTime timeStamp;

}
