package ru.vtb.msa.rfrm.integration.checking.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneDto {

    /**
     * Тип телефона в соответствии с таблицей "Общие справочные значения для взаимодействия".
     */
    private String type;

    /**
     * Номер телефона. Формат числовой без пробелов (79101234567)
     */
    private String number;
}
