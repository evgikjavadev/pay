package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.updatephoneoutputmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@Value
@JsonDeserialize(builder = UpdatePhoneMessageResponse.UpdatePhoneMessageResponseBuilder.class)
public class UpdatePhoneMessageResponse {
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
