package ru.vtb.msa.rfrm.integration.util.client;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ResponseObjWebClient {

    private List<String> mdmIdFromHeader;
    private String responseWebClient;

}
