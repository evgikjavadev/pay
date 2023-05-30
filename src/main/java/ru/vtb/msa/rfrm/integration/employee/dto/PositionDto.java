package ru.vtb.msa.rfrm.integration.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные о должности сотрудника
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDto {
    /**
     * Идентификатор должности
     */
    private String id;

    /**
     * Полное наименование должности
     */
    private String fullName;
}