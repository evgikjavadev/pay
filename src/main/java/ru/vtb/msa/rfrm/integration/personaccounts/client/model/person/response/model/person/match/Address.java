package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model.person.match;

import lombok.Data;

@Data
public class Address {

    /**
     * Признак - доверительная система.
     */
    private Boolean trustFlag;

    /**
     * Код типа адреса (Справочник MDM «Тип адреса»).
     */
    private String personalAddressType;

    /**
     * Полный неочищенный адрес (Адрес «как есть», не прошедший  процесс очистки/стандартизации средствами Фактор).
     */
    private String addressName;

    /**
     * Ненормализованный адрес.
     */
    private String notFormAddrName;

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
    private String stateNumber;

    /**
     * Тип области (ОБЛ/КРАЙ).
     */
    private String stateType;

    /**
     * Область с типом (МОСКОВСКАЯ).
     */
    private String personalPrefState;

    /**
     * Тип района (Р-Н).
     */
    private String districtType;

    /**
     * Район с типом (КРАСНОГОРСКИЙ).
     */
    private String personalPrefDistrict;

    /**
     * Тип города (Г).
     */
    private String cityType;

    /**
     * Город с типом (КРАСНОГОРСК).
     */
    private String personalPrefCity;

    /**
     * Тип улицы (УЛ).
     */
    private String streetType;

    /**
     * Тип населенного пункта (МКР).
     */
    private String settlementType;

    /**
     * Населенный пункт с типом (ОПАЛИХА).
     */
    private String personalPrefSettlement;

    /**
     * Улица с типом (ЦВЕТАЕВОЙ).
     */
    private String personalPrefStreet;

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
    private String apartamentNumber;

    /**
     * Код КЛАДР (50013001001010000).
     */
    private String kladrCode;

    /**
     * Код ОКАТО (46223501000).
     */
    private String okatoCode;

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
     * Наименование системы источника в которой производились  изменения.
     */
    private String nameExternalSystem;

    /**
     * Дата изменения в системе источнике  (в формате MM/DD/YYYY HH24:MI:SS ).
     */
    private String updateDate;
}
