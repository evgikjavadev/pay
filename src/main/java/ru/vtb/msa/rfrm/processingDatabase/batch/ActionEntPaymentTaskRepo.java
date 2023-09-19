package ru.vtb.msa.rfrm.processingDatabase.batch;

import java.util.List;
import java.util.UUID;

public interface ActionEntPaymentTaskRepo {

    // обновление списка rewardId, установка для каждого rewardId blocked=1 и blocked_at=now()
    void updateBlockByUUIDEqualOne(List<UUID> entities);

    // обновление списка rewardId, установка для каждого rewardId blocked=0 и blocked_at=now()
    void updateBlockByUUIDEqualZero(List<UUID> entities);

}
