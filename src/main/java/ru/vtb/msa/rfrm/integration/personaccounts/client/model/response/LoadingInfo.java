package ru.vtb.msa.rfrm.integration.personaccounts.client.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoadingInfo {
    @JsonProperty("info")
    private String info;
    @JsonProperty("statusMode")
    private String statusMode;
    @JsonProperty("subSystemUserId")
    private String subSystemUserId;
    @JsonProperty("subSystems")
    private String subSystems;


}
