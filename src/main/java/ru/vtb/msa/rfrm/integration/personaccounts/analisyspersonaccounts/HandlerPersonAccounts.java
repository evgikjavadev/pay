package ru.vtb.msa.rfrm.integration.personaccounts.analisyspersonaccounts;

public class HandlerPersonAccounts {

    private TestAccounts testAccounts;


    private TestAccounts getTestAccounts() {
        TestAccounts testAccounts = TestAccounts
                .builder()
                .entityType("MASTER_ACCOUNT")
                .currency("RUB")
                .isArrested(false)
                .build();
        return testAccounts;
    }
    void getMasterAccount() {
        if (getTestAccounts().getEntityType().equals("MASTER_ACCOUNT")
                && getTestAccounts().getCurrency().equals("RUB")
                && getTestAccounts().getIsArrested().equals(false)) {



        }
    }
}
