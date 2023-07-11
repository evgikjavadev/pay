//package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request.closephoneinputmodel;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.Value;
//
//import javax.validation.constraints.AssertTrue;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@Builder
//@Value
//@JsonDeserialize(builder = ContactPhoneCommunication.ContactPhoneCommunicationBuilder.class)
//public class ContactPhoneCommunication {
//    /**
//     * Неформализованный номер телефона
//     */
//    @Schema(description = "Неформализованный номер телефона: XXX XXXXXXX", example = "123 1234567")
//    @JsonProperty("notFormFullPhone")
//    @NotNull
//    @Size(min = 11, max = 11)
//    @Pattern(regexp = "\\d{3}\\s\\d{7}") // XXX XXXXXXX
//    String notFormFullPhone;
//    /**
//     * Код типа телефона
//     */
//    @Schema(description = "Код типа телефона, всегда равно 6", example = "6")
//    @JsonProperty("phoneType")
//    @NotNull
//    @Size(max = 1)
//    @Pattern(regexp = "6")
//    String phoneType;
//    /**
//     * Наименование системы источника в которой производились изменения
//     */
//    @Schema(description = "Наименование системы источника в которой производились изменения, всегда равно EMS)", example = "EMS")
//    @JsonProperty("nameExternalSystem")
//    @NotNull
//    @Size(min = 4, max = 4)
//    String nameExternalSystem;
//    /**
//     * Флаг доверительной системы
//     */
//    @Schema(description = "Флаг доверительной системы (всегда true)", example = "true")
//    @JsonProperty("trustFlag")
//    @NotNull
//    @AssertTrue
//    boolean trustFlag;
//    /**
//     * Дата окончания действия
//     */
//    @Schema(description = "Дата окончания действия, в формате YYYY-MM-DD, заполняется при закрытии телефона",maxLength = 10, format = "date",example = "2022-06-06")
//    @JsonProperty("endDate")
//    LocalDate endDate;
//    /**
//     * Наименование учетной записи пользователя, производившего изменения в системе источнике
//     */
//    @Schema(description = "Табельный номер сотрудника, инициировавшего вызов операции. Если операция инициирована в результате выполнения автоматических процессов без участия ФЛ - передавать значение \"system\"", example = "system")
//    @JsonProperty("loginExternalSystem")
//    @NotNull
//    @Size(max = 100)
//    String loginExternalSystem;
//    /**
//     * Дата изменения в системе источнике
//     */
//    @Schema(description = "Дата изменения в системе источнике, в формате yyyy-MM-dd'T'HH:mm:ss.f'Z', заполняется на стороне системы",format = "date-time", example = "2021-12-17T18:14:47.150Z")
//    @JsonProperty("updateDate")
//    @NotNull
//    LocalDateTime updateDate;
//}
