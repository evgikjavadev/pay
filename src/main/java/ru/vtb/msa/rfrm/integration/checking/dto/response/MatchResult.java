package ru.vtb.msa.rfrm.integration.checking.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResult {

    /**
     * Идентификатор фигуранта (если есть в ЧС).
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String recordId;

    /**
     * Идентификатор записи фигуранта (если есть в ЧС).
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rowIdUnic;

    /**
     * ФИО / Наименование.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    /**
     * Тип субъекта.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;

    /**
     * Дата рождения.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String birthDate;

    /**
     * Место рождения.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String birthPlace;

    /**
     * Гражданство.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String citizens;

    /**
     * Серия и номер паспорта.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String passport;

    /**
     * Кем выдан паспорт.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String issuingAuthority;

    /**
     * Коэффициент совпадения.
     */
    private String matchTypeCd;

    /**
     * Тип совпадения (например: ФИО + дата рождения, ИНН и т.д.), сценарий.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String matchType;

    /**
     * Поисковое значение.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String searchValue;

    /**
     * Системное наименование ЧС .
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String blName;

    /**
     * Владелец списка.
     */
    private String blOwner;

    /**
     * Дата и время проверки.
     */
    private String checkDate;

    /**
     * Адреса.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;

    /**
     * Тип арбитража.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String arbitration;

    /**
     * Сообщение.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
}
