package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountsInfo {
    private List<Alias> alias;
    private List<String> deposites;
    private List<String> cards;
    private List<String> investments;
    private List<String> investmentContracts;
    private List<String> loans;
    private List<PackMult> packages;

    private List<String> insuranceContracts;
    private List<String> selfEmploymentInfo;
    private List<String> wallets;
    private List<String> technicalAccounts;
    private String[] relations;
    private List<String> ibsContracts;
    private String system;

    /** Уникальный идентификатор клиента (mdm_id) */
    private String userId;

    private LocalDateTime timestamp;
    private String result;


}
