package ru.vtb.msa.rfrm.connectionDatabaseJdbc.model;


public enum DctStatusDetails {

    CLIENT_NOT_FOUND_IN_MDM(101, 4, "Продуктовый Профиль ФЛ: клиент не найден в МДМ"),
    NOT_CORRECT_REQUEST(103, 4, "Продуктовый Профиль ФЛ: некорректный запрос к сервису"),
    ERROR_ACCESS(104, 4, "Продуктовый Профиль ФЛ: ошибка доступа"),
    MASTER_ACCOUNT_NOT_FOUND(201, 3, "Продуктовый Профиль ФЛ: Мастер-счет не найден"),
    MASTER_ACCOUNT_ARRESTED(202, 3, "Продуктовый Профиль ФЛ: Мастер-счет арестован"),
    ERR_REQUIREMENTS(203, 3, "Продуктовый Профиль ФЛ: Не соблюдены требования к участию в акции");

    private final Integer statusDetailsCode;
    private final Integer relatedStatus;
    private final String description;
    DctStatusDetails(Integer statusDetailsCode, Integer relatedStatus, String description) {
        this.statusDetailsCode = statusDetailsCode;
        this.relatedStatus = relatedStatus;
        this.description = description;
    }

    public Integer getStatusDetailsCode() {
        return statusDetailsCode;
    }

    public Integer getRelatedStatus() {
        return relatedStatus;
    }

    public String getDescription() {
        return description;
    }

}
