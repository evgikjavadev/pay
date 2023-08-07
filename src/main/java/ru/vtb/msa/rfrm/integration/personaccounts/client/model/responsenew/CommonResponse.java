package ru.vtb.msa.rfrm.integration.personaccounts.client.model.responsenew;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class CommonResponse {
    @JsonProperty("accounts")
    private Accounts accounts;

    @JsonProperty("deposites")
    public Deposites deposites;
    @JsonProperty("cards")
    public Cards cards;
    @JsonProperty("investments")
    public Investments investments;
//    @JsonProperty("investmentContracts")
//    public InvestmentContracts investmentContracts;
//    @JsonProperty("loans")
//    public Loans loans;
//    @JsonProperty("packages")
//    public PackagesAcc packages;
//    @JsonProperty("insuranceContracts")
//    public InsuranceContracts insuranceContracts;
//    @JsonProperty("selfEmploymentInfo")
//    public SelfEmploymentInfo selfEmploymentInfo;
//    @JsonProperty("wallets")
//    public Wallets wallets;
//    @JsonProperty("technicalAccounts")
//    public TechnicalAccounts technicalAccounts;
//    @JsonProperty("relations")
//    public List<Object> relations;
//    @JsonProperty("ibsContracts")
//    public IbsContracts ibsContracts;
    @JsonProperty("system")
    public String system;
    @JsonProperty("userId")
    public String userId;
    @JsonProperty("timestamp")
    public String timestamp;
    @JsonProperty("result")
    public String result;
}
