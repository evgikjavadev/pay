package ru.vtb.msa.rfrm.integration.personcs.client.model.person.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = ContactReference.ContactReferenceBuilder.class)
public class ContactReference {
    /**
     * ИД клиента в КС
     */
    @JsonProperty("externalId")
    @Schema(description = "EMS_ID абонента в системе ВТБ МОБАЙЛ")
    @NotNull
    @Size(min = 1, max = 64)
    private String externalId;

    /**
     * Код системы
     */
    @JsonProperty("systemNumber")
    @Schema(description = "Код системы, всегда равно EMS")
    @NotNull
    @Size(min = 3, max = 4)
    private String systemNumber;
}
