package ru.vtb.msa.rfrm.integration.personcs.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.person.Segment;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
public class PersonModel {
    /**
     * Глобальный идентификатор записи Customer HUB.
     * ИД клиента в МДМ (УИК).
     */
    private String partyUId;

    /**
     * Дата изменения в системе источнике.
     */
    private LocalDateTime updateDate;

    /**
     * Дата создания карточки в системе источнике.
     */
    private LocalDateTime startDate;

    /**
     * Код типа клиента.
     */
    private String type;

    /**
     * Пол.
     */
    private String genderCode;

    /**
     * Дата рождения.
     */
    private LocalDateTime birthDateTime;

    /**
     * Место рождения.
     */
    private String birthPlace;

    /**
     * Имя (в русскоязычном формате).
     */
    private String firstName;

    /**
     * Отчество (в русскоязычном формате).
     */
    private String middleName;

    /**
     * Фамилия (в русскоязычном формате).
     */
    private String lastName;

    /**
     * Фамилия латиницей (в русскоязычном формате).
     */
    private String lastNameLat;

    /**
     * Имя латиницей (в русскоязычном формате).
     */
    private String firstNameLat;

    /**
     * Фамилия Имя Отчество в родительном падеже (в русскоязычном формате).
     */
    private String fullNameGenitive;

    /**
     * Фамилия Имя Отчество в дательном падеже (в русскоязычном формате).
     */
    private String fullNameDative;

    /**
     * Фамилия Имя Отчество в творительном падеже (в русскоязычном формате).
     */
    private String fullNameAblative;

    /**
     * Признак резидента.
     */
    private Boolean rezidentFlag;

    /**
     * Код гражданства.
     */
    private String nationalityCountryCode;

    /** ИНН клиента*/
    private String inn;

    /**
     * Табельный номер сотрудника.
     */
    private String employerCode;

    /**
     * Информация по документам.
     */
    private IdentityDocInfoModel personDocumentIdentity;

    /**
     * Информация по адресам.
     */
    private AddressInfoModel address;

    /**
     * Информация по телефонам.
     */
    private PhoneInfoModel contactPhoneCommunication;

    /**
     * Email клиента.
     */
    private EmailInfoModel emailCommunication;

    /**
     * СНИЛС
     */
    private IdentityDocInfoModel snils;

    /**
     * Сегмент.
     */
    private List<Segment> segment;
}
