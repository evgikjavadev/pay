package ru.vtb.msa.rfrm.service;

import ru.vtb.msa.rfrm.integration.rfrmkafka.model.CorePayKafkaModel;

import java.util.List;

public interface PrepareProcessGetAccounts {
    void firstStepProcessAccounts(List<CorePayKafkaModel> messagesFromKafka);
}
