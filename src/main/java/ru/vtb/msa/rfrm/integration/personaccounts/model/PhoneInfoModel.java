package ru.vtb.msa.rfrm.integration.personaccounts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PhoneInfoModel {

    /**
     * Полный номер телефона.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String completeNumber;

    /**
     * Неформализованный номер телефона.
     */
    private String notFormFullPhone;

    /**
     * Код типа телефона.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phoneType;
}
