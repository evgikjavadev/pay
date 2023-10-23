package ru.vtb.msa.rfrm.processingDatabase.batch;

import java.util.List;

public interface ActionEntPaymentTaskRepo {

    // обновление списка rewardId, установка для каждого rewardId blocked=1 и blocked_at=now()
    void updateBlockByRewardIdEqualOne(List<Long> entities);

    // обновление списка rewardId, установка для каждого rewardId blocked=0 и blocked_at=now()
    void updateBlockByRewardIdEqualZero(List<Long> entities);

}
