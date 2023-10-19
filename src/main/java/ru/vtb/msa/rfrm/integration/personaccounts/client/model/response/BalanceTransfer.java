package ru.vtb.msa.rfrm.integration.personaccounts.client.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BalanceTransfer {
    @JsonProperty("balanceTransferAvailableSum")
    private Double balanceTransferAvailableSum;
}
