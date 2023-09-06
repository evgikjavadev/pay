package ru.vtb.msa.rfrm.integration.personaccounts.client.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Overdraft {
    @JsonProperty("status")
    private Boolean status;
    @JsonProperty("ownBalance")
    private Double ownBalance;
    @JsonProperty("principalDebt")
    private Double principalDebt;
    @JsonProperty("pastDueTotal")
    private Double pastDueTotal;

}
