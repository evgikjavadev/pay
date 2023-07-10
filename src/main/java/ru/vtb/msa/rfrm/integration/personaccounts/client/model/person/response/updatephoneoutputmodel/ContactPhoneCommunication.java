package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.updatephoneoutputmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@Value
@JsonDeserialize(builder = ContactPhoneCommunication.ContactPhoneCommunicationBuilder.class)
public class ContactPhoneCommunication {
    /**
     * Полный номер телефона
     */
    @JsonProperty("completeNumber")
    String completeNumber;
    /**
     * Код типа телефона
     */
    @JsonProperty("phoneType")
    String phoneType;
}
