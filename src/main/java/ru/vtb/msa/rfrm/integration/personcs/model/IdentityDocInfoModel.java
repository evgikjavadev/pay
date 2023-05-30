package ru.vtb.msa.rfrm.integration.personcs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class IdentityDocInfoModel {
    /**
     * Серия.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String series;

    /**
     * Номер.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String number;

    /**
     * Страна выдачи.
     */
    private String issueCountryCode;

    /**
     * Код типа документа.
     *
     * Паспорт СССР	1
     * Заграничный паспорт СССР	2
     * Свидетельство о рождении	3
     * Удост лич военнослужащего	4
     * Справка об освобождении	5
     * Паспорт Минморфлота	6
     * Дип. Паспорт гражданина РФ	9
     * Служебный паспорт гражданина РФ	100
     * Временное уд личности гр-а РФ	14
     * Паспорт РФ	21
     * Заграничный паспорт РФ	22
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String typeCode;

    /**
     * Кем выдан.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String issueName;

    /**
     * Код подразделения.
     */
    private String issueCode;

    /**
     * Дата выдачи.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate issueDate;

    /**
     * Дата окончания.
     */

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    /**
     * Код наименования системы источника в которой производились изменения.
     */

    private String nameExternalSystem;

    /**
     * Дата начала действия.
     */

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    /**
     * Дата окончания действия.
     */

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    /**
     * Наименование учетной записи пользователя, производившего изменения в системе источнике.
     */

    private String loginExternalSystem;

    /**
     * Дата изменения в системе источнике.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updateDate;

    /**
     * Дата начала срока действия права пребывания (проживания) в Российской Федерации (В формате MM/DD/YYYY).
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startOfRightToStay;

    /**
     * Дата окончания срока действия права пребывания (проживания) в Российской Федерации (В формате MM/DD/YYYY).
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endOfRightToStay;

    /**
     * Признак - доверительная система.
     */
    private Boolean trustFlag;
}
