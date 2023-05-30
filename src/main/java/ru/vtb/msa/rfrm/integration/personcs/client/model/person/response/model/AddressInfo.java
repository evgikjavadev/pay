package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressInfo {

    /**
     * Код типа адреса.
     * <p>
     * Не указан	                    0
     * Постоянной регистрации	        1
     * Временной регистрации	        2
     * Фактический	                    3
     * Адрес регистрации работодателя	4
     * Фактический рабочий	            9
     * Почтовый	                        10
     */
    private String typeCode;

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
    private LocalDate startDateofRegistration;

    /**
     * Дата окончания действия.
     */
    private LocalDate endDateofRegistration;

    /**
     * Наименование учетной записи пользователя, производившего изменения в системе источнике.
     */
    private String loginExternalSystem;

    /**
     * Дата изменения в системе источнике.
     */
    private LocalDate updateDate;

    /**
     * Ненормализованный адрес.
     */
    private String notFormAddrName;

    /**
     * Полный неочищенный адрес.
     */
    private String fullAddress;

    /**
     * Страна.
     */
    private String country;

    /**
     * Почтовый индекс.
     */
    private String zipCode;

    /**
     * Регион (номер региона).
     */
    private String state;

    /**
     * Тип области (ОБЛ/КРАЙ).
     */
    private String provinceType;

    /**
     * Область с типом (МОСКОВСКАЯ).
     */
    private String province;

    /**
     * Тип района (Р-Н).
     */
    private String regionType;

    /**
     * Район с типом (КРАСНОГОРСКИЙ).
     */
    private String region;

    /**
     * Тип города (Г).
     */
    private String cityType;

    /**
     * Город с типом (КРАСНОГОРСК).
     */
    private String city;

    /**
     * Тип улицы (УЛ).
     */
    private String streetType;

    /**
     * Улица с типом (ЦВЕТАЕВОЙ).
     */
    private String street;

    /**
     * Тип населенного пункта (МКР).
     */
    private String locationType;

    /**
     * Населенный пункт с типом (ОПАЛИХА).
     */
    private String location;

    /**
     * Дом (9).
     */
    private String house;

    /**
     * Корпус.
     */
    private String housing;

    /**
     * Строение.
     */
    private String building;

    /**
     * Квартира/Офис (1).
     */
    private String apartmentNumber;

    /**
     Код КЛАДР
     */
    @JsonProperty("kLADR")
    private String kladr;

    /**
     Код ОКАТО
     */
    @JsonProperty("oKATO")
    private String okato;
}
