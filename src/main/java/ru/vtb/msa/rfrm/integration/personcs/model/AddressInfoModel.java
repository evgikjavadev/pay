package ru.vtb.msa.rfrm.integration.personcs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class AddressInfoModel {
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateofRegistration;

    /**
     * Дата окончания действия.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDateofRegistration;

    /**
     * Наименование учетной записи пользователя, производившего изменения в системе источнике.
     */
    private String loginExternalSystem;

    /**
     * Дата изменения в системе источнике.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;

    /**
     * Ненормализованный адрес.
     */
    private String notFormAddrName;

    /**
     * Полный неочищенный адрес.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fullAddress;

    /**
     * Страна.
     */
    private String country;

    /**
     * Почтовый индекс.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String zipCode;

    /**
     * Регион (номер региона).
     */
    private String state;


    private String provinceType;


    private String province;


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

    private String kladr;

    private String okato;
}
