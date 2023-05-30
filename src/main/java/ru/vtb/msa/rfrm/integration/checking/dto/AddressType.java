package ru.vtb.msa.rfrm.integration.checking.dto;

import ru.vtb.msa.rfrm.integration.checking.CheckingException;
import ru.vtb.msa.rfrm.integration.checking.CheckingExceptionType;

public enum AddressType {

    /**
     * Адрес постоянной регистрации.
     */
    REGISTER("RegisterAddress"),

    /**
     * Адрес временной регистрации.
     */
    TEMPORARY_REGISTER("TemporaryRegisterAddress"),

    /**
     * Адрес фактического проживания.
     */
    FACT("FactAddress"),

    /**
     * Адрес регистрации работодателя.
     */
    WORK_REGISTER("WorkRegistAddress"),

    /**
     * Фактический рабочий.
     */
    WORK_ADDRESS("WorkAddress"),

    /**
     * Почтовый адрес.
     */
    POST("PostAddress"),

    /**
     * Тип не указан.
     */
    NOT_SPECIFIED("NotSpecifiedAddress");

    private final String type;

    AddressType(String code) {
        this.type = code;
    }

    public static String getByCodeAddress(String typeCodeAddress) {
        switch (typeCodeAddress) {
            case ("0"):
                throw new CheckingException(CheckingExceptionType.ADDRESS_NOT_FOUND,
                        "Не найдена информация об адресе клиента");
            case ("1"):
                return REGISTER.type;
            case ("2"):
                return TEMPORARY_REGISTER.type;
            case ("3"):
                return FACT.type;
            case ("4"):
                return WORK_REGISTER.type;
            case ("9"):
                return WORK_ADDRESS.type;
            case ("10"):
                return POST.type;
            default:
                return NOT_SPECIFIED.type;
        }
    }
}