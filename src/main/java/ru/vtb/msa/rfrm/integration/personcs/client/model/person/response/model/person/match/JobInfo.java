package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.person.match;

import lombok.Data;

/**
 * Информация о работе.
 */
@Data
public class JobInfo {

    /**
     * Полное наименование организации.
     */
    private String employerOrganizationName;

    /**
     * ИНН/КИО организации работодателя.
     */
    private String taxPayerIdentificationNumber;

    /**
     * Адрес места работы.
     */
    private String fullAddress;

    /**
     * Код типа занятости.
     */
    private String employmentType;

    /**
     * Код типа трудового договора.
     */
    private String employmentContractType;

    /**
     * Должность.
     */
    private String jobTitle;

    /**
     * Дата начала трудового договора (В формате MM/DD/YYYY HH24:MI:SS ).
     */
    private String startDate;

    /**
     * Дата окончания трудового договора (В формате MM/DD/YYYY HH24:MI:SS ).
     */
    private String endDate;
}
