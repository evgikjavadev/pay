package ru.vtb.msa.rfrm.integration.arbitration.client;

import ru.vtb.msa.rfrm.integration.arbitration.dto.request.ArbitrationRequest;
import ru.vtb.msa.rfrm.integration.arbitration.dto.response.ArbitrationResponse;

/**
 * Клиент для вызова Управление Арбитражем по сделке 1906.
 */

public interface ArbitrationClient {
    ArbitrationResponse call(ArbitrationRequest request);
}