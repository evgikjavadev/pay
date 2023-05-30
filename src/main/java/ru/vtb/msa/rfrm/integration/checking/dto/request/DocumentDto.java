package ru.vtb.msa.rfrm.integration.checking.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class DocumentDto {

    /**
     * Тип документа для ФЛ (PassportRF).
     */
    private String type;

    /**
     * Серия обязательна в случае паспорта РФ, передается без пробелов и тире.
     */
    private String series;

    /**
     * Номер документа.
     */
    private String number;

    /**
     * Кем выдан документ .
     */
    private String issuingAuthority;

    /**
     * Дата выдачи.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate issuingDate;
}
