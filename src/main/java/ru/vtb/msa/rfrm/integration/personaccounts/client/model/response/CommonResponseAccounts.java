package ru.vtb.msa.rfrm.integration.personaccounts.client.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseAccounts<R> {
    @JsonProperty("accounts")
    private Map<String, Account> accounts;
    @JsonProperty("deposites")
    private Deposites deposites;
    @JsonProperty("cards")
    public Cards cards;
    @JsonProperty("investments")
    public Investments investments;
    @JsonProperty("investmentContracts")
    public InvestmentContracts investmentContracts;
    @JsonProperty("loans")
    public Loans loans;
    @JsonProperty("packages")
    public PackagesAcc packages;
    @JsonProperty("insuranceContracts")
    public InsuranceContracts insuranceContracts;
    @JsonProperty("selfEmploymentInfo")
    public SelfEmploymentInfo selfEmploymentInfo;
    @JsonProperty("wallets")
    public Wallets wallets;
    @JsonProperty("loadingInfo")
    private LoadingInfo loadingInfo;
    @JsonProperty("technicalAccounts")
    public TechnicalAccounts technicalAccounts;
    @JsonProperty("userId")
    public String userId;

    @JsonProperty("timestamp")
    public String timestamp;
    @JsonProperty("result")
    public String result;
    @JsonProperty("system")
    public String system;
    @JsonProperty("statusMode")
    public String statusMode;
    @JsonProperty("errorString")
    public String errorString;
    @JsonProperty("relations")
    public List<Object> relations;
    @JsonProperty("ibsContracts")
    public IbsContracts ibsContracts;

}
