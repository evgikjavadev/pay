package ru.vtb.msa.rfrm.connectionDatabaseJdbc.model;


public enum DctStatusDetails {

    CLIENT_NOT_FOUND_IN_MDM(101, "Продуктовый Профиль ФЛ: клиент не найден в МДМ"),
    NOT_CORRECT_REQUEST(103, "Продуктовый Профиль ФЛ: некорректный запрос к сервису"),
    ERROR_ACCESS(104, "Продуктовый Профиль ФЛ: ошибка доступа"),
    MASTER_ACCOUNT_NOT_FOUND(201, "Продуктовый Профиль ФЛ: Мастер-счет не найден"),
    MASTER_ACCOUNT_ARRESTED(202, "Продуктовый Профиль ФЛ: Мастер-счет арестован"),
    ERR_REQUIREMENTS(203, "Продуктовый Профиль ФЛ: Не соблюдены требования к участию в акции");

    private final Integer statusDetailsCode;
    private final String description;
    DctStatusDetails(Integer statusDetailsCode, String description) {
        this.statusDetailsCode = statusDetailsCode;
        this.description = description;
    }

    public Integer getStatusDetailsCode() {
        return statusDetailsCode;
    }


    public String getDescription() {
        return description;
    }

}
