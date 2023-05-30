package ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.updatephoneinputmodel.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePhonePcHeaderRequest {
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
     * Логин пользователя (доп. требования по заполнению не предъявляются, используется для логирования)
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String contactName;
    /**
     * Таймаут (В формате SS секунд, без разделителей)
     */
    private int timeout;
}

