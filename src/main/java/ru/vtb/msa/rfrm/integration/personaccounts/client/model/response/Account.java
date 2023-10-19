package ru.vtb.msa.rfrm.integration.personaccounts.client.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Data
public class Account {
    @JsonProperty("balance")
    private Balance balance;
    @JsonProperty("blockedAmount")
    private Double blockedAmount;
    @JsonProperty("closeDate")
    private LocalDate closeDate;
    @JsonProperty("entityId")
    private String entityId;
    @JsonProperty("entityLastTrans")
    private String entityLastTrans;
    @JsonProperty("entityLocalId")
    private String entityLocalId;
    @JsonProperty("entityName")
    private String entityName;
    @JsonProperty("expireDate")
    private LocalDate expireDate;
    @JsonProperty("externalIdPart1")
    private String externalIdPart1;
    @JsonProperty("favourite")
    private Boolean favourite;
    @JsonProperty("vtbPay")
    private Boolean vtbPay;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("mayCredit")
    private Boolean mayCredit;
    @JsonProperty("mayDebit")
    private Boolean mayDebit;
    @JsonProperty("openDate")
    private String openDate;
    @JsonProperty("organisationUnit")
    private String organisationUnit;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("source")
    private String source;
    @JsonProperty("version")
    private LocalDateTime version;
    @JsonProperty("overdraft")
    private Overdraft overdraft;
    @JsonProperty("timestamp")
    private String timestamp;

    /** Идентификатор пользователя в учетной системе */
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("visible")
    private Boolean visible;
    @JsonProperty("monthProfit")
    private Double monthProfit;
    @JsonProperty("currentAccount")
    private String currentAccount;
    @JsonProperty("subProductType")
    private String subProductType;
    @JsonProperty("systemInstance")
    private String systemInstance;
    @JsonProperty("accountTypeDescription")
    private String accountTypeDescription;
    @JsonProperty("region")
    private String region;
    @JsonProperty("statusDetail")
    private String statusDetail;
    @JsonProperty("number")
    private String number;
    @JsonProperty("balanceTransfer")
    private BalanceTransfer balanceTransfer;
    @JsonProperty("maskedNumber")
    private String maskedNumber;
    @JsonProperty("salePoint")
    private String salePoint;
    @JsonProperty("accountType")
    private String accountType;
    @JsonProperty("entityType")
    private String entityType;
    @JsonProperty("balanceType")
    private String balanceType;
    @JsonProperty("status")
    private String status;
    @JsonProperty("state")
    private String state;
    @JsonProperty("entitySubSystems")
    private String entitySubSystems;
    @JsonProperty("publicId")
    private String publicId;
    @JsonProperty("rate")
    private Double rate;
    @JsonProperty("inMigrationProcess")
    private Boolean inMigrationProcess;
    @JsonProperty("isMigrated")
    private Boolean isMigrated;
    @JsonProperty("migrationDate")
    private LocalDate migrationDate;
    @JsonProperty("mir")
    private Boolean mir;
    @JsonProperty("isArrested")
    private Boolean isArrested;

}
