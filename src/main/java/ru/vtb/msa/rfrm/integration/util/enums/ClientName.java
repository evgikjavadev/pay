package ru.vtb.msa.rfrm.integration.util.enums;

import lombok.Getter;

/**
 * Наименование интеграционных клиентов
 */
public enum ClientName {
    CLIENT_DKO("1503 Продуктовый профиль ФЛ (PRP)"),
    CONSENT("1522 Согласия ФЛ (ASCM)"),
    CROSS_REF("1443 Кросс-ссылки ФЛ (CSCR)"),
    EMPLOYEE("1777 Карточка сотрудника (BSCS)"),
    DOSSIER("1497 Досье ЮЛ/ФЛ (DOSE)"),
    COMPLEX_CHECK("1721 Комплексная проверка (CPSL)"),
    SESSION_DATA("1408 Сессионные данные (TSCT)"),
    PERSON_CS("1442 Карточка ФЛ (CSPC)"),
    EPA_ANTI_REPLAY("1473 Единая платформа аутентификации"),
    EPA_AUTH("1473 Единая платформа аутентификации"),
    SLK_CLIENT("404 Система логистики карт (PLM)"),
    SPLM_CLIENT("1848 Учет и логистика персопродуктов (SPLM)"),
    ROLE_MODEL("1440 Ролевая модель (TSRM)"),
    CALL_ARBITRATION("1907 Управление арбитражем по сделке (УАПС)");

    @Getter
    private final String value;

    ClientName(String value) {
        this.value = value;
    }
}