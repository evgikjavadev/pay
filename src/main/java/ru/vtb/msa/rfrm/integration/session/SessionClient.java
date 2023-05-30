package ru.vtb.msa.rfrm.integration.session;

import java.util.Map;

/**
 * Клиент для получения данных из СС Сессионные данные.
 */
public interface SessionClient {
    /**
     * Получение сохраненных данных в сессии в виде словаря.
     *
     * @param session уникальный идифкатор сессии.
     * @return данные сохраненные в сессии или {@link } если сессии не существует.
     */
    Map<String, Map<String, String>> getSessionMap(String session);
}