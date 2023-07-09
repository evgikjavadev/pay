package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.person.FaultMessage;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.person.JobInfo;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.person.Segment;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.person.match.RelativeInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPersonData {
    /**
     * Найденные клиенты
     */
    private List<String> person;

    /**
     * Сообщение об ошибке
     */
    private FaultMessage faultMessage;

//    @Data
//    public static class Person {
//        /**
//         * Глобальный идентификатор записи Customer HUB.
//         * ИД клиента в МДМ (УИК).
//         */
//        private String partyUId;
//
//        /**
//         * Наименование учетной записи пользователя, производившего изменения в системе источнике.
//         */
//        private String loginExternalSystem;
//
//        /**
//         * Код наименования системы источника в которой производились изменения.
//         */
//        private String nameExternalSystem;
//
//        /**
//         * Дата изменения в системе источнике.
//         */
//        private LocalDateTime updateDate;
//
//        /**
//         * Дата создания карточки в системе источнике.
//         */
//        private LocalDateTime startDate;
//
//        /**
//         * Согласие на предоставление данных БКИ кредитной  организации - кредитору.
//         */
//        private Boolean consentCreditBureauVerification;
//
//        /**
//         * Согласие на передачу данных в БКИ.
//         */
//        private Boolean consentCreditBureauUpload;
//
//        /**
//         * Код типа клиента.
//         */
//        private String type;
//
//        /**
//         * Код статуса.
//         */
//        private String status;
//
//        /**
//         * Пол.
//         */
//        private String genderCode;
//
//        /**
//         * Семейное положение.
//         */
//        private String maritalStatusCode;
//
//        /**
//         * Дата рождения.
//         */
//        private LocalDateTime birthDateTime;
//
//        /**
//         * Дата смерти.
//         */
//        private LocalDateTime deathDateTime;
//
//        /**
//         * Место рождения.
//         */
//        private String birthPlace;
//
//        /**
//         * Имя (в русскоязычном формате).
//         */
//        private String firstName;
//
//        /**
//         * Отчество (в русскоязычном формате).
//         */
//        private String middleName;
//
//        /**
//         * Фамилия (в русскоязычном формате).
//         */
//        private String lastName;
//
//        /**
//         * Фамилия латиницей (в русскоязычном формате).
//         */
//        private String lastNameLat;
//
//        /**
//         * Имя латиницей (в русскоязычном формате).
//         */
//        private String firstNameLat;
//
//        /**
//         * Фамилия Имя Отчество в родительном падеже (в русскоязычном формате).
//         */
//        private String fullNameGenitive;
//
//        /**
//         * Фамилия Имя Отчество в дательном падеже (в русскоязычном формате).
//         */
//        private String fullNameDative;
//
//        /**
//         * Фамилия Имя Отчество в творительном падеже (в русскоязычном формате).
//         */
//        private String fullNameAblative;
//
//        /**
//         * Статус публичного должностного лица.
//         */
//        private String pubOfficialStatus;
//
//        /**
//         * Признак заблокированного (удаленного) клиента.
//         */
//        private String terminated;
//
//        /**
//         * Предыдущая Фамилия.
//         */
//        private String previousLastName;
//
//        /**
//         * Предыдущее Имя.
//         */
//        private String previousFirstName;
//
//        /**
//         * Предыдущее Отчество.
//         */
//        private String previousMiddleName;
//
//        /**
//         * Дата изменения ФИО.
//         */
//        private LocalDateTime changeDateFullName;
//
//        /**
//         * ИНН.
//         */
//        @JsonProperty("tIN")
//        private String inn;
//
//        /**
//         * Код гражданства.
//         */
//        private String nationalityCountryCode;
//
//        /**
//         * Налоговый резидент.
//         */
//        private Boolean taxRezident;
//
//        /**
//         * Признак резидента.
//         */
//        private Boolean rezidentFlag;
//
//        /**
//         * Код типа сотрудника.
//         */
//        private String bankEmployee;
//
//        /**
//         * Табельный номер сотрудника.
//         */
//        private String employerCode;
//
//        /**
//         * Число детей.
//         */
//        private String childrenNumber;
//
//        /**
//         * Число иждевенцев.
//         */
//        private String dependentNumber;
//
//        /**
//         * Общее число членов домохозяйства.
//         */
//        private String householderNumber;
//
//        /**
//         * Номер налоговой инспекции.
//         */
//        private String numTaxInspection;
//
//        /**
//         * Полис ОМС.
//         */
//        @JsonProperty("OMS")
//        private String OMS;
//
//        /**
//         * Дата планового обновления сведений (В формате yyyy-mm-dd).
//         */
//        private LocalDate plannedUpdateDate;
//
//        /**
//         * Уровень риска.
//         */
//        private String riskLevel;
//
//        /**
//         * Обоснование уровня риска.
//         */
//        private List<RiskLevelJustification> riskLevelJustification;
//
//        /**
//         * Информация о работе.
//         */
//        private List<JobInfo> employment;
//
//        /**
//         * Информация об образовании.
//         */
//        private List<EducationInfo> education;
//
//        /**
//         * Сведения о семье.
//         */
//        private List<RelativeInfo> relatives;
//
//        /**
//         * Информация по документам.
//         */
//        private List<IdentityDocInfo> personDocumentIdentity;
//
//        /**
//         * Информация по адресам.
//         */
//        private List<AddressInfo> address;
//
//        /**
//         * Информация по телефонам.
//         */
//        private List<PhoneInfo> contactPhoneCommunication;
//
//        /**
//         * Email клиента.
//         */
//        private List<EmailInfo> emailCommunication;
//
//        /**
//         * Информация о связях клиента.
//         */
//        private List<Relationship> contactRelationship;
//
//        /**
//         * Сегмент.
//         */
//        private List<Segment> segment;
//
//        /**
//         * Сегмент "Чёрные списки".
//         */
//        private List<BlackListItem> blackList;
//    }
}
