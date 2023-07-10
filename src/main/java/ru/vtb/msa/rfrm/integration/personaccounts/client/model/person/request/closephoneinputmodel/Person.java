package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.closephoneinputmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import javax.validation.Valid;
import java.util.List;

@Getter
@Builder
@Value
@JsonDeserialize(builder = Person.PersonBuilder.class)
public class Person {
    /**
     * Информация по телефонам
     */
    @Schema(description = "Информация по телефонам")
    @JsonProperty("contactPhoneCommunication")
    @Valid
    List<ContactPhoneCommunication> contactPhoneCommunication;
}
