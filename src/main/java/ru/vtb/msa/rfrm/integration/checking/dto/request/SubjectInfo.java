package ru.vtb.msa.rfrm.integration.checking.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class SubjectInfo {

    /**
     * Генерирует система-потребитель в формате UUID.
     */
    private String subjectId;

    /**
     * ID клиента в фронтальной системе. Передается в случае взаимодействия с ОС "Комплаенс".
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String fsClientId;

    /**
     * Тип субъекта. Должно совпадать с тем, что было указано в согласованной стратегии (Person).
     */
    private String subjectType;

    /**
     * Сегмент субъекта. Должно совпадать с тем, что было указано в согласованной стратегии.
     */
    private String subjectSegment;

    /**
     * Тип сущности. Должно совпадать с тем, что было указано в согласованной стратегии и согласовано с потребителем.
     * Если у субъекта main=false, Комплексная проверка передает в Комплаенс EntityType=«Связанное лицо».
     */
    private String entityType;

    /**
     * Наименование роли субъекта. Должно совпадать с тем, что было указано
     * в согласованной стратегии и согласовано с потребителем.
     */
    private String roleType;

    /**
     * Только у основного субъекта в пакете возможен признак main=true. У остальных должен при этом быть false.
     * В наших запросах всегда true.
     */
    private boolean main;

    /**
     * Фамилия субъекта проверки.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String lastName;

    /**
     * Имя субъекта проверки.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String firstName;

    /**
     * Отчество субъекта проверки.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String patronymic;

    /**
     * Дата рождения субъекта проверки. Формат yyyy-MM-dd (1990-09-04).
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private LocalDate birthDate;

    /**
     * Признак нерезидента
     */
    private Boolean nonResident;
    /**
     * Фамилия на латинице
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastNameLatin;
    /**
     * Имя на латинице
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstNameLatin;
    /**
     * СНИЛС.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pensionAccount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nationality;

    /** ИНН клиента */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String inn;

    /**
     * Документы клиента. Обязательно для осуществления проверок.
     *
     * Как минимум одна запись в листе обязательна для subjectType= Person (type= PassportRF)
     */
    private List<DocumentDto> documents;

    /**
     * Адреса клиента. Как минимум одна запись в листе обязательна для subjectType= Person.
     */
    private List<AddressDto> addresses;

    /**
     * Телефоны клиента. Как минимум одна запись в листе обязательна для subjectType= Person.
     */
    private List<PhoneDto> phones;
}