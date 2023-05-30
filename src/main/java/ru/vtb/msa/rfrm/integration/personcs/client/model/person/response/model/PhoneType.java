package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model;

/**
 * Справочник "Телефона"
 * <p>
 * Не указан	                            0<p>
 * Домашний	                                1<p>
 * Контактный	                            2<p>
 * Фактический	                            3<p>
 * По месту работы	                        4<p>
 * Мобильный рабочий	                    5<p>
 * Мобильный личный	                        6<p>
 * Для нотификаций	                        7<p>
 * Телефон АТМ	                            15<p>
 * Телефон по месту временной регистрации	14<p>
 * Телефон по месту постоянной регистрации	8<p>
 * Основной телефон для оповещений БО	    16<p>
 * Дополнительный телефон для оповещений БО	17<p>
 */
public enum PhoneType {

    PERSONAL("6"),

    NOTIFICATION("7");

    private final String typeCode;

    PhoneType(String typeCode) {
        this.typeCode = typeCode;
    }

    public static boolean isPersonal(String code) {
        return PERSONAL.typeCode.equals(code);
    }

    public static boolean isNotification(String code) {
        return NOTIFICATION.typeCode.equals(code);
    }

    public String getTypeCode() {
        return typeCode;
    }
}
