package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.person.match;

import lombok.Data;

import java.time.LocalDate;

/**
 * Сведения о близком родственнике.
 */
@Data
public class RelativeInfo {

    /**
     * ФИО.
     */
    private String fIO;

    /**
     * Дата рождения.
     */
    private LocalDate birthDateTime;

    /**
     * На иждивении.
     */
    private Boolean dependentFlag;

    /**
     * Степень родства.
     */
    private String relationDegree;
}
