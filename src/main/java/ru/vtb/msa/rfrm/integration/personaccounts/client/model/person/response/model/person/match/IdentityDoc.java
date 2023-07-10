package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model.person.match;

import lombok.Data;

/**
 * Документ клиента.
 */
@Data
public class IdentityDoc {

    /**
     * Признак - доверительная система.
     */
    private Boolean trustFlag;

    /**
     * Код типа документа.Справочник MDM «Тип документа» 12.
     */
    private String type;

    /**
     * Значение типа документа.
     */
    private String typeValue;

    /**
     * Серия.
     */
    private String series;

    /**
     * Номер.
     */
    private String number;

    /**
     * Страна выдачи.
     */
    private String countryIssue;

    /**
     * Кем выдан.
     */
    private String issued;

    /**
     * Дата выдачи (в формате MM/DD/YYYY).
     */
    private String issueDate;

    /**
     * Дата окончания действия (в формате MM/DD/YYYY).
     */
    private String expirationDate;

    /**
     * Код подразделения.
     */
    private String divisionCode;

    /**
     * Примечание.
     */
    private String comment;

    /**
     * Дата начала действия (в формате MM/DD/YYYY).
     */
    private String startDate;

    /**
     * Дата окончания действия (в формате MM/DD/YYYY).
     */
    private String endDate;

    /**
     * Наименование учетной записи пользователя, производившего  изменения в системе источнике.
     */
    private String loginExternalSystem;

    /**
     * Код наименования системы источника в которой  производились изменения.
     */
    private String nameExternalSystem;

    /**
     * Дата изменения в системе источнике  (в формате MM/DD/YYYY HH24:MI:SS ).
     */
    private String updateDate;
}
