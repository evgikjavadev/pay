package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.closephoneoutputmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@Value
@JsonDeserialize(builder = ClosePhoneMessageResponse.ClosePhoneMessageResponseBuilder.class)
public class ClosePhoneMessageResponse {
    /**
     * Корневой элемент блока с ошибками
     */
    @JsonProperty("faultMessage")
    FaultMessage faultMessage;
    /**
     * Информация о ФЛ
     */
    @JsonProperty("person")
    Person person;
}
