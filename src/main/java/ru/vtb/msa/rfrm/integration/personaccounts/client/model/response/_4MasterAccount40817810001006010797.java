//package ru.vtb.msa.rfrm.integration.personaccounts.client.model.response;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//import javax.annotation.Generated;
//import javax.validation.Valid;
//import com.fasterxml.jackson.annotation.JsonAnyGetter;
//import com.fasterxml.jackson.annotation.JsonAnySetter;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//        "timestamp",
//        "state",
//        "entitySubSystems",
//        "number",
//        "entityType",
//        "entityId",
//        "entityLocalId",
//        "entityName",
//        "openDate",
//        "balance",
//        "mayDebit",
//        "mayCredit",
//        "userId",
//        "organisationUnit",
//        "branch",
//        "externalIdPart1",
//        "publicId",
//        "productId",
//        "favourite",
//        "visible",
//        "vtbPay",
//        "version",
//        "detailAction",
//        "systemInstance",
//        "status",
//        "overdraft",
//        "mir",
//        "blockedAmount",
//        "isArrested",
//        "maskedNumber",
//        "inMigrationProcess",
//        "salePoint",
//        "installmentPlan"
//})
//@Generated("jsonschema2pojo")
//public class _4MasterAccount40817810001006010797 {
//
//    @JsonProperty("timestamp")
//    public String timestamp;
//    @JsonProperty("state")
//    public String state;
//    @JsonProperty("entitySubSystems")
//    public String entitySubSystems;
//    @JsonProperty("number")
//    public String number;
//    @JsonProperty("entityType")
//    public String entityType;
//    @JsonProperty("entityId")
//    public String entityId;
//    @JsonProperty("entityLocalId")
//    public String entityLocalId;
//    @JsonProperty("entityName")
//    public String entityName;
//    @JsonProperty("openDate")
//    public String openDate;
//    @JsonProperty("balance")
//    @Valid
//    public Balance balance;
//    @JsonProperty("mayDebit")
//    public Boolean mayDebit;
//    @JsonProperty("mayCredit")
//    public Boolean mayCredit;
//    @JsonProperty("userId")
//    public String userId;
//    @JsonProperty("organisationUnit")
//    public String organisationUnit;
//    @JsonProperty("branch")
//    public String branch;
//    @JsonProperty("externalIdPart1")
//    public String externalIdPart1;
//    @JsonProperty("publicId")
//    public String publicId;
//    @JsonProperty("productId")
//    public String productId;
//    @JsonProperty("favourite")
//    public Boolean favourite;
//    @JsonProperty("visible")
//    public Boolean visible;
//    @JsonProperty("vtbPay")
//    public Boolean vtbPay;
//    @JsonProperty("version")
//    public String version;
//    @JsonProperty("detailAction")
//    public String detailAction;
//    @JsonProperty("systemInstance")
//    public String systemInstance;
//    @JsonProperty("status")
//    public String status;
//    @JsonProperty("overdraft")
//    @Valid
//    public Overdraft overdraft;
//    @JsonProperty("mir")
//    public Boolean mir;
//    @JsonProperty("blockedAmount")
//    public Double blockedAmount;
//    @JsonProperty("isArrested")
//    public Boolean isArrested;
//    @JsonProperty("maskedNumber")
//    public String maskedNumber;
//    @JsonProperty("inMigrationProcess")
//    public Boolean inMigrationProcess;
//    @JsonProperty("salePoint")
//    public String salePoint;
//    @JsonProperty("installmentPlan")
//    @Valid
//    public InstallmentPlan installmentPlan;
//    @JsonIgnore
//    @Valid
//    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
//
//    @JsonAnyGetter
//    public Map<String, Object> getAdditionalProperties() {
//        return this.additionalProperties;
//    }
//
//    @JsonAnySetter
//    public void setAdditionalProperty(String name, Object value) {
//        this.additionalProperties.put(name, value);
//    }
//
//}
