package ru.vtb.msa.rfrm.integration.personaccounts.client.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class Account {
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("state")
    private String state;
    @JsonProperty("entitySubSystems")
    private String entitySubSystems;
    @JsonProperty("number")
    private String number;
    @JsonProperty("entityType")
    private String entityType;
    @JsonProperty("entityId")
    private String entityId;
    @JsonProperty("entityLocalId")
    private String entityLocalId;
    @JsonProperty("entityName")
    private String entityName;
    @JsonProperty("openDate")
    private String openDate;
    @JsonProperty("balance")
    private Balance balance;
    @JsonProperty("mayDebit")
    private Boolean mayDebit;
    @JsonProperty("mayCredit")
    private Boolean mayCredit;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("organisationUnit")
    private String organisationUnit;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("externalIdPart1")
    private String externalIdPart1;
    @JsonProperty("publicId")
    private String publicId;
    @JsonProperty("favourite")
    private Boolean favourite;
    @JsonProperty("visible")
    private Boolean visible;
    @JsonProperty("vtbPay")
    private Boolean vtbPay;
    @JsonProperty("version")
    private String version;
    @JsonProperty("detailAction")
    private String detailAction;
    @JsonProperty("systemInstance")
    private String systemInstance;
    @JsonProperty("status")
    private String status;
    @JsonProperty("overdraft")
    private Overdraft overdraft;
    @JsonProperty("mir")
    private Boolean mir;
    @JsonProperty("blockedAmount")
    private Double blockedAmount;
    @JsonProperty("isArrested")
    private Boolean isArrested;
    @JsonProperty("maskedNumber")
    private String maskedNumber;
    @JsonProperty("inMigrationProcess")
    private Boolean inMigrationProcess;
    @JsonProperty("salePoint")
    private String salePoint;
    @JsonProperty("installmentPlan")
    private InstallmentPlan installmentPlan;
}
