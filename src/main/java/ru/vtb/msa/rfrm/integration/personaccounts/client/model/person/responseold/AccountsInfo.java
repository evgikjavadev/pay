package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.responseold;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.*;

@Data
public class AccountsInfo {
    private Accounts accounts;
    private Deposites deposites;
    private Cards cards;
    private Investments investments;
    private InvestmentContracts investmentContracts;
    private Loans loans;
    private PackagesAcc packages;
    private InsuranceContracts insuranceContracts;
    private SelfEmploymentInfo selfEmploymentInfo;
    private Wallets wallets;
    private TechnicalAccounts technicalAccounts;
    private String[] relations;
    private IbsContracts ibsContracts;
    private String system;

    /** Уникальный идентификатор клиента (mdm_id) */
    private String userId;
    private String timestamp;
    private String result;


}
