package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Информация по телефону.
 */
@Data
public class PhoneInfo {

    /**
     * Полный номер телефона.
     */
    private String completeNumber;

    /**
     * Неформализованный номер телефона.
     */
    private String notFormFullPhone;

    /**
     * Код типа телефона.
     */
    private String phoneType;

    /**
     * Верифицирован (будет добавлено в Р4 2021) (Y - Подтвержденный телефон N - Неподтвержденный).
     */
    private String verificated;

    /**
     * Дата верификации (В формате MM/DD/YYYY) (будет добавлено в Р4 2021).
     */
    private String verificationDate;

    /**
     * Наименование системы источника в которой производились изменения.
     */
    private String nameExternalSystem;

    /**
     * Флаг доверительной системы.
     */
    private Boolean trustFlag;

    /**
     * Дата начала действия.
     */
    private LocalDateTime startDate;

    /**
     * Дата окончания действия.
     */
    private LocalDateTime endDate;

    /**
     * Наименование учетной записи пользователя, производившего изменения в системе источнике.
     */
    private String loginExternalSystem;

    /**
     * Дата изменения в системе источнике.
     */
    private LocalDateTime updateDate;

    /**
     * Код качества телефона.
     */
    private String qualityCode;

    /**
     * Регион телефонаАтрибут восстанавливается по номеру телефона, в процессе очистки/стандартизации.
     */
    private String areaName;

    /**
     * Наименование оператора.
     */
    private String providerName;
}
