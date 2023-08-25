package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"accounts",
		"deposites",
		"cards",
		"investments",
		"investmentContracts",
		"loans",
		"packages",
		"insuranceContracts",
		"selfEmploymentInfo",
		"wallets",
		"technicalAccounts",
		"relations",
		"ibsContracts",
		"system",
		"userId",
		"timestamp",
		"result"
})
@Generated("jsonschema2pojo")
public class ResponseCommon {

	@JsonProperty("accounts")
	@Valid
	public Accounts accounts;
	@JsonProperty("deposites")
	@Valid
	public Deposites deposites;
	@JsonProperty("cards")
	@Valid
	public Cards cards;
	@JsonProperty("investments")
	@Valid
	public Investments investments;
	@JsonProperty("investmentContracts")
	@Valid
	public InvestmentContracts investmentContracts;
	@JsonProperty("loans")
	@Valid
	public Loans loans;
	@JsonProperty("packages")
	@Valid
	public PackagesAcc packages;
	@JsonProperty("insuranceContracts")
	@Valid
	public InsuranceContracts insuranceContracts;
	@JsonProperty("selfEmploymentInfo")
	@Valid
	public SelfEmploymentInfo selfEmploymentInfo;
	@JsonProperty("wallets")
	@Valid
	public Wallets wallets;
	@JsonProperty("technicalAccounts")
	@Valid
	public TechnicalAccounts technicalAccounts;
	@JsonProperty("relations")
	@Valid
	public List<Object> relations;
	@JsonProperty("ibsContracts")
	@Valid
	public IbsContracts ibsContracts;
	@JsonProperty("system")
	public String system;
	@JsonProperty("userId")
	public String userId;
	@JsonProperty("timestamp")
	public String timestamp;
	@JsonProperty("result")
	public String result;
	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}