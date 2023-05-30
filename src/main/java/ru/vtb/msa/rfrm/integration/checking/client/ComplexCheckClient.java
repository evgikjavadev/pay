package ru.vtb.msa.rfrm.integration.checking.client;

import ru.vtb.msa.rfrm.integration.checking.dto.request.ComplexCheckRequest;
import ru.vtb.msa.rfrm.integration.checking.dto.response.ComplexCheckResponse;

/**
 * Клиент для OC Комплексная проверка 1721
 */

public interface ComplexCheckClient {

    /**
     * Проведение проверки паспорта ФЛ и нахождения его в санкционных списках
     */
    ComplexCheckResponse check(ComplexCheckRequest request);
}