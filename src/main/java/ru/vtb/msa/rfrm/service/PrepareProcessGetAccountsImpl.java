package ru.vtb.msa.rfrm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.integration.rfrmkafka.model.CorePayKafkaModel;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PrepareProcessGetAccountsImpl implements PrepareProcessGetAccounts {
    private final ServiceAccountsInterface serviceAccountsInterface;
    @Override
    public void firstStepProcessAccounts(List<CorePayKafkaModel> messagesFromKafka) {

        for (CorePayKafkaModel elem: messagesFromKafka) {
            Long mdmId = elem.getMdmId();
            Integer rewardId = elem.getRewardId();
            serviceAccountsInterface.getClientAccounts(mdmId, rewardId);
        }

    }
}
