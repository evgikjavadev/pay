package ru.vtb.msa.rfrm.integration.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentDto {
    /**
     * Идентификатор подразделения
     */
    private String id;

    /**
     * Полное наименование подразделения
     */
    private String fullName;

    /**
     * Иерархия подразделения
     */
    private String fullNameHierarchy;

    /**
     * Иерархия подразделения ? ? ?
     */
    private String idHierarchy;
}
