package ru.vtb.msa.rfrm.integration.checking.dto;

public enum SubjectType {
    /**
     * ФЛ.
     */
    PERSON("Person"),

    /**
     * ЮЛ.
     */
    ORGANIZATION("Organization"),

    /**
     * ИП.
     */
    IP("IndividualEntrepreneur");

    private final String type;

    SubjectType(String code) {
        this.type = code;
    }

    public String getType() {
        return type;
    }
}
