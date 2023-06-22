package ru.vtb.msa.rfrm.integration.util.enums;

import lombok.Getter;

/**
 * Наименование интеграционных клиентов
 */
public enum ClientName {
    PRODUCT_PROFILE_FL("1503 Продуктовый профиль ФЛ");
    @Getter
    private final String value;

    ClientName(String value) {
        this.value = value;
    }
}