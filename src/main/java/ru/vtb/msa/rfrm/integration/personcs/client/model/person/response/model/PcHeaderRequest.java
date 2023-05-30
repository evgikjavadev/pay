package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * Корневой элемент тех. заголовка.
 */
@Data
@Builder
public class PcHeaderRequest {

    /**
     * Логин пользователя.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contactName;

    /**
     * Дата и время формирования сообщения (MM/DD/YYYY HH:MM:SS).
     */
    private String creationDateTime;

    /**
     * Идентификатор сообщения.
     */
    private String messageID;

    /**
     * Идентификатор системы-потребителя.
     */
    private String systemFrom;

    /**
     * Идентификатор системы-поставщика.
     */
    private final String systemTo = "CustomerHub";

    /**
     * Время таймаута (В формате SS секунд, без разделителей).
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer timeout;
}
