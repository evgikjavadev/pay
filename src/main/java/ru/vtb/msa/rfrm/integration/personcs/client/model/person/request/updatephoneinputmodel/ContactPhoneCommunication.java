package ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.updatephoneinputmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@JsonDeserialize(builder = ContactPhoneCommunication.ContactPhoneCommunicationBuilder.class)
public class ContactPhoneCommunication {
    /**
     * Неформализованный номер телефона
     */
    @Schema(description = "Неформализованный номер телефона: XXXXXXXXXX", minLength = 11, maxLength = 1 ,required = true, example = "123 1234567")
    @JsonProperty("notFormFullPhone")
    @NotNull
    @Size(min = 11, max = 11)
    String notFormFullPhone;
    /**
     * Неформализованный номер телефона
     */
    @Schema(description = "Формализованный номер телефона: XXX XXXXXXX", example = "123 1234567")
    @JsonProperty("completeNumber")
    String completeNumber;
    /**
     * Код типа телефона
     */
    @Schema(description = "Код типа телефона, всегда равно 6", required = true, example = "6")
    @JsonProperty("phoneType")
    @NotNull
    @Size(max = 1)
    @Pattern(regexp = "6")
    String phoneType;
    /**
     * Наименование системы источника в которой производились изменения
     */
    @Schema(description = "Наименование системы источника в которой производились изменения, всегда равно EMS)",required = true, example = "EMS")
    @JsonProperty("nameExternalSystem")
    @NotNull
    @Size(min = 3, max = 4)
    String nameExternalSystem;
    /**
     * Флаг доверительной системы
     */
    @Schema(description = "Флаг доверительной системы (всегда true)", required = true, example = "true")
    @JsonProperty("trustFlag")
    @NotNull
    @AssertTrue
    boolean trustFlag;
    /**
     * Дата начала действия
     */
    @Schema(description = "Дата начала действия, в формате YYYY-MM-DD, заполняется при обновлении телефона",
            pattern = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", type = "string", maxLength = 10, example = "2022-06-06")
    @JsonProperty("startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    LocalDate startDate;

    /**
     * Дата окончания действия
     */
    @Schema(description = "Дата окончания действия, в формате YYYY-MM-DD, заполняется при обновлении телефона",
            pattern = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", type = "string", maxLength = 10, example = "2022-06-06")
    @JsonProperty("endDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    LocalDate endDate;
    /**
     * Наименование учетной записи пользователя, производившего изменения в системе источнике
     */
    @Schema(description = "Табельный номер сотрудника, инициировавшего вызов операции. Если операция инициирована в результате выполнения автоматических процессов без участия ФЛ - передавать значение \"system\"", example = "system")
    @JsonProperty("loginExternalSystem")
    @NotNull
    @Size(max = 100)
    String loginExternalSystem;
    /**
     * Дата изменения в системе источнике
     */
    @Schema(description = "Дата изменения в системе источнике, в формате YYYY-MM-DDThh:mm:ss.fZ, заполняется на стороне системы", format = "date-time",example = "2021-12-17T18:14:47.150Z")
    @JsonProperty("updateDate")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @NotNull
    String updateDate;
}

