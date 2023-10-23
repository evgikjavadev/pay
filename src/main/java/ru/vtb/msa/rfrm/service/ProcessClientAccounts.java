package ru.vtb.msa.rfrm.service;

import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Account;

public interface ProcessClientAccounts {
    void processAccounts(Account account, String result, Long mdmId, Long rewardId);
}
