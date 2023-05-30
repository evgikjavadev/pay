package ru.vtb.msa.rfrm.integration.checking.dto;

public enum EntityType {

    /**
     * Является клиентом банка.
     */
    CLIENT("Клиент"),

    /**
     * Является потенциальным клиентом банка.
     */
    PROSPECT("Проспект");

    private final String type;

    EntityType(String type) {
        this.type = type;
    }

    public static String getByPersonType(String personType) {
        if (personType.equals("3")) {
            return PROSPECT.type;
        } else {
            return CLIENT.type;
        }
    }
}