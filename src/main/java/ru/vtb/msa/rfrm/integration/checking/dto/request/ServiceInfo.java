package ru.vtb.msa.rfrm.integration.checking.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceInfo {

    /**
     * Канал поступления объекта, прописанные в стратегии. На данный момент только SP.
     */
    private String entryChannel;

    /**
     * Тематика продукта. Константа 1847_MOBC_1.
     */
    private String product;

    /**
     * Валюта продукта. Данный атрибут должен совпадать со справочником валют в матрице Compliance (ALL).
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String currency;

    /**
     * ФИО сотрудника (если отсутствует employeeFullname, то можно передать любую константу).
     */
    private String employeeFullname;

    /**
     * Электронный адрес сотрудника (если отсутствует employeeFullname, то можно передать любую константу).
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String employeeEmail;
}
