package ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.updatephoneinputmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.validation.Valid;
import java.util.List;

@Data
@Builder
@Value
@JsonDeserialize(builder = Person.PersonBuilder.class)
public class Person {
    /**
     * Информация по телефонам
     */
    @ArraySchema(maxItems = 10, minItems = 1, schema = @Schema(implementation = ContactPhoneCommunication.class, description = "Информация по телефонам"))
    @JsonProperty("contactPhoneCommunication")
    @Valid
    List<ContactPhoneCommunication> contactPhoneCommunication;
}



