package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.closephoneoutputmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@Value
@JsonDeserialize(builder = Person.PersonBuilder.class)
public class Person {
    /**
     * Информация по телефонам
     */
    @JsonProperty("contactPhoneCommunication")
    ContactPhoneCommunication contactPhoneCommunication;
}
