package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model;

import lombok.Builder;
import lombok.Data;

/**
 * Общая обертка для запросов к PresonCs.
 *
 * @param <T> Тип корневого элемент запроса.
 */
@Data
@Builder
public class PcRequest<T> {

//    /**
//     * Корневой элемент тех. заголовка.
//     */
//    protected PcHeaderRequest headerRequest;

    /**
     * Корневой элемент бизнес заголовка.
     */
    protected T productTypes;
}
