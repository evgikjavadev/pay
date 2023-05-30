package ru.vtb.msa.rfrm.integration.checking.dto;

public enum RoleType {

    /**
     * Является клиентом банка.
     */
    CLIENT("Клиент"),

    /**
     * Является потенциальным клиентом банка.
     */
    POTENTIAL("Потенциальный клиент");

    private final String type;

    RoleType(String type) {
        this.type = type;
    }

    public static String getByPersonType(String personType) {
        if (personType.equals("3")) {
            return POTENTIAL.type;
        } else {
            return CLIENT.type;
        }
    }
}