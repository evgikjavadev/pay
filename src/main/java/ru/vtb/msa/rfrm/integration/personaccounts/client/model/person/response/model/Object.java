package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model;

import lombok.Builder;
import lombok.Data;

/**
 * Общая обертка для запросов к PresonCs.
 *
 * @param <T> Тип корневого элемент запроса.
 */
@Data
@Builder
public class Object<T> {

//    /**
//     * Корневой элемент тех. заголовка.
//     */
//    protected PcHeaderRequest headerRequest;

    /**
     * Корневой элемент бизнес заголовка.
     */
    protected T productTypes;
}
