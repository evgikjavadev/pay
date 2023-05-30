package ru.vtb.msa.rfrm.integration.checking.dto;


import ru.vtb.msa.rfrm.integration.checking.CheckingException;
import ru.vtb.msa.rfrm.integration.checking.CheckingExceptionType;

public enum PhoneType {

    /**
     * Домашний телефон.
     */
    PERMANENT_ADDRESS("PermanentAddressPhone"),

    /**
     * Телефон по месту постоянной регистрации.
     */
    REGISTER_ADDRESS("RegisterAddressPhone"),

    /**
     * Контактный телефон.
     */
    CONTACT("Contact"),

    /**
     * Телефон по адресу временной регистрации.
     */
    TEMPORARY_ADDRESS("TemporaryAddressPhone"),

    /**
     * Фактический номер.
     */
    FACT_ADDRESS("FactAddressPhone"),

    /**
     * По месту работы.
     */
    WORK_ADDRESS("WorkAddressPhone"),

    /**
     * Мобильный личный.
     */
    MOBILE("MobilePhone"),

    /**
     * Мобильный рабочий.
     */
    MOBILE_WORK("MobileWork");

    private final String type;

    PhoneType(String code) {
        this.type = code;
    }

    public static String getByPhoneCode(String phoneCode) {
        switch (phoneCode) {
            case ("0"):
                throw new CheckingException(CheckingExceptionType.PHONE_NOT_FOUND,
                        "Не найдена информация о телефонах клиента");
            case ("1"):
                return PERMANENT_ADDRESS.type;
            case ("3"):
                return FACT_ADDRESS.type;
            case ("4"):
                return WORK_ADDRESS.type;
            case ("5"):
                return MOBILE_WORK.type;
            case ("6"):
                return MOBILE.type;
            case ("8"):
                return REGISTER_ADDRESS.type;
            case ("14"):
                return TEMPORARY_ADDRESS.type;
            default:
                return CONTACT.type;
        }
    }
}