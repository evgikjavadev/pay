package ru.vtb.msa.rfrm.integration.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassportDto {

    /**
     * Серия паспорта
     */
    private String series;

    /**
     * Номер паспорта
     */
    private String number;

    /**
     * Дата выдачи паспорта в формате ГГГГ-ММ-ДД
     */
    private String issueDate;

    /**
     * Подразделение, выдавшее паспорт
     */
    private String issuer;

    /**
     * Код подразделения, выдавшего паспорт
     */
    private String issuerCode;
}