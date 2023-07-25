package ru.vtb.msa.rfrm.integration.personaccounts.analisyspersonaccounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestAccounts {
    private String entityType;
    private String currency;
    private Boolean isArrested;

}
