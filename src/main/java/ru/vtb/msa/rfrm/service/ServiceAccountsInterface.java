package ru.vtb.msa.rfrm.service;

public interface ServiceAccountsInterface {
    void getClientAccounts(Long mdmIdFromKafka, Long rewardId);
}
