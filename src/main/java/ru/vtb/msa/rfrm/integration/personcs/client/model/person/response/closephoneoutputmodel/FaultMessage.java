package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.closephoneoutputmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@Value
@JsonDeserialize(builder = FaultMessage.FaultMessageBuilder.class)
public class FaultMessage {
    /**
     * Статус ошибки
     */
    @JsonProperty("status")
    int status;
    /**
     * Текст ошибки
     */
    @JsonProperty("text")
    String text;
    /**
     * Описание ошибки
     */
    @JsonProperty("description")
    String description;
}
