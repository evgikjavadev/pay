package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model.person.match;

import lombok.Data;

/**
 * Телефон клиента.
 */
@Data
public class Phone {

    /**
     * Флаг доверительной системы.
     */
    private Boolean trustFlag;

    /**
     * Код типа телефона.Справочник MDM «Тип телефона».
     */
    private String phoneType;

    /**
     * Наименование оператора.
     */
    private String operatorName;

    /**
     * Регион телефона Атрибут восстанавливается по номеру телефона, в процессе очистки/стандартизации.
     */
    private String phoneRegion;

    /**
     * Признак "Верифицирован" (будет добавлено в Р4 2021) (Y - Подтвержденный телефон N - Неподтвержденный).
     */
    private String verificated;

    /**
     * Дата верификации (в формате MM/DD/YYYY) (будет добавлено в Р4 2021).
     */
    private String verificationDate;

    /**
     * Дата начала действия (в формате MM/DD/YYYY).
     */
    private String startDate;

    /**
     * Дата окончания действия (в формате MM/DD/YYYY).
     */
    private String endDate;

    /**
     * Наименование учетной записи пользователя, производившего изменения в системе источнике.
     */
    private String loginExternalSystem;

    /**
     * Наименование системы источника в которой производились изменения.
     */
    private String nameExternalSystem;

    /**
     * Дата изменения в системе источнике  (в формате MM/DD/YYYY HH24:MI:SS ).
     */
    private String updateDate;

    /**
     * Значение типа телефона.
     */
    private String phoneValue;

    /**
     * Полный номер телефона.
     */
    private String fullPhone;

    /**
     * Полный неочищенный номер телефона.
     */
    private String notFormFullPhone;
}
