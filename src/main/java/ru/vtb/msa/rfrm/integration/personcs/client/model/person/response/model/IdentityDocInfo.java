//package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model;
//
//import lombok.Data;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
///**
// * Информация по документам.
// */
//@Data
//public class IdentityDocInfo {
//
//    /**
//     * Серия.
//     */
//    private String series;
//
//    /**
//     * Номер.
//     */
//    private String number;
//
//    /**
//     * Страна выдачи.
//     */
//    private String issueCountryCode;
//
//    /**
//     * Код типа документа.
//     *
//     * Паспорт СССР	1
//     * Заграничный паспорт СССР	2
//     * Свидетельство о рождении	3
//     * Удост лич военнослужащего	4
//     * Справка об освобождении	5
//     * Паспорт Минморфлота	6
//     * Дип. Паспорт гражданина РФ	9
//     * Служебный паспорт гражданина РФ	100
//     * Временное уд личности гр-а РФ	14
//     * Паспорт РФ	21
//     * Заграничный паспорт РФ	22
//     */
//    private String typeCode;
//
//    /**
//     * Кем выдан.
//     */
//    private String issueName;
//
//    /**
//     * Код подразделения.
//     */
//    private String issueCode;
//
//    /**
//     * Дата выдачи.
//     */
//    private LocalDate issueDate;
//
//    /**
//     * Дата окончания.
//     */
//    private LocalDate expirationDate;
//
//    /**
//     * Код наименования системы источника в которой производились изменения.
//     */
//    private String nameExternalSystem;
//
//    /**
//     * Дата начала действия.
//     */
//    private LocalDateTime startDate;
//
//    /**
//     * Дата окончания действия.
//     */
//    private LocalDateTime endDate;
//
//    /**
//     * Наименование учетной записи пользователя, производившего изменения в системе источнике.
//     */
//    private String loginExternalSystem;
//
//    /**
//     * Дата изменения в системе источнике.
//     */
//    private LocalDateTime updateDate;
//
//    /**
//     * Дата начала срока действия права пребывания (проживания) в Российской Федерации (В формате MM/DD/YYYY).
//     */
//    private LocalDate startOfRightToStay;
//
//    /**
//     * Дата окончания срока действия права пребывания (проживания) в Российской Федерации (В формате MM/DD/YYYY).
//     */
//    private LocalDate endOfRightToStay;
//
//    /**
//     * Признак - доверительная система.
//     */
//    private Boolean trustFlag;
//}
