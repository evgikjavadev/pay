package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model;

/**
 * Код типа документа.
 */
public enum IdDocType {

    /**
     * Паспорт СССР.
     */
    USSR("1"),

    /**
     * Паспорт РФ.
     */
    RUS("21"),

    /**
     * Пенсионное страховое свидетельство.
     */
    SNILS("300");

    private final String code;

    IdDocType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Boolean isRus(String code){
        return RUS.code.equals(code);
    }

    public static Boolean isSnils(String code){
        return SNILS.code.equals(code);
    }
}
