package ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.closephoneinputmodel.http;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClosePhonePcHeaderRequest {
    /**
     * Идентификатор сообщения
     */
    private String messageID;
    /**
     * Идентификатор системы-потребителя (код РИС системы, которая посылает запрос)
     */
    private String systemFrom;
    /**
     * Идентификатор системы-поставщика (константа "396")
     */
    private String systemTo;
    /**
     * Дата и время формирования сообщения
     */
    private String creationDateTime;
    /**
     * Таймаут (В формате SS секунд, без разделителей)
     */
    private int timeout;
}

