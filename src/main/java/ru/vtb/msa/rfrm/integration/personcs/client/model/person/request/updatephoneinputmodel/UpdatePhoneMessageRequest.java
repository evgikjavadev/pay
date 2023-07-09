//package ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.updatephoneinputmodel;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Builder;
//import lombok.Data;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.util.List;
//
//@Data
//@Builder
//@Schema(description = "Объект для обработки сценария UPDATE_PHONE_NUMBER")
//@JsonDeserialize(builder = UpdatePhoneMessageRequest.UpdatePhoneMessageRequestBuilder.class)
//public class UpdatePhoneMessageRequest {
//    /**
//     * Уникальный идентификатор клиента в МДМ
//     */
//    @Schema(description = "Уникальный идентификатор клиента в МДМ", example = "1234567890")
//    @JsonProperty("partyUId")
//    @NotNull
//    @Size(min = 5, max = 30)
//    String partyUId;
//    /**
//     * Обновление телефонов
//     */
//    @Schema(description = "Обновление телефонов, равно true", example = "true")
//    @JsonProperty("updatePhone")
//    @NotNull
//    Boolean updatePhone;
//
//    /** Обновление даты */
//    @Schema(description = "Дата изменения в системе источнике, в формате YYYY-MM-DDThh:mm:ss.fZ, заполняется на стороне системы", format = "date-time",example = "2021-12-17T18:14:47.150Z")
//    @JsonProperty("updateDate")
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//    @NotNull
//    String updateDate;
//
//    /**
//     * Информация о ФЛ
//     */
//    @ArraySchema(maxItems = 10, minItems = 1, schema = @Schema(implementation = Person.class, description = "Информация о ФЛ"))
//    @JsonProperty("person")
//    @NotNull
//    @Valid
//    List<Person> person;
//}
//
//
//
