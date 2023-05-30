package ru.vtb.msa.rfrm.integration.arbitration.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.vtb.msa.rfrm.integration.checking.dto.request.AddressDto;
import ru.vtb.msa.rfrm.integration.checking.dto.request.DocumentDto;
import ru.vtb.msa.rfrm.integration.checking.dto.request.PhoneDto;
import ru.vtb.msa.rfrm.integration.checking.dto.response.MatchResult;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class SubjectDetailsList {

    /** Данные, полученные из 1721 Комплексная проверка: UUID. */
    private String subjectId;

    /** Данные, полученные из 1408 Сессионные данные: Контейнер клиентской сессии.mdm_id. */
    private String clientId;

    /** Система в которой ведется клиент. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clientSystemCode;

    /** Данные, полученные из 1721 Комплексная проверка. */
    private String subjectType;

    /** Данные, полученные из 1721 Комплексная проверка. */
    private String entityType;

    /** Данные, полученные из 1721 Комплексная проверка. */
    private String roleType;

    /** Данные, полученные из 1721 Комплексная проверка: Полное наименование ЮЛ. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    /** Данные, полученные из 1442 Карточка ФЛ. */

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    /** Данные, полученные из 1442 Карточка ФЛ. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    /** Данные, полученные из 1442 Карточка ФЛ. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String patronymic;

    /** Данные, полученные из 1721 Комплексная проверка. */
    private String decision;

    /** Данные, полученные из 1721 Комплексная проверка. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String comment;

    /** Данные, полученные из 1721 Комплексная проверка. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean arbitration;

    /** Данные, полученные из 1721 Комплексная проверка: Код стратегии принятия решения. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String strategy;

    /** Является основным субъектом или связанным лицом. */
    private boolean main;

    /** Данные, полученные из 1442 Карточка ФЛ: Сегмент субъекта. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subjectSegment;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nationality;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String inn;

    /** Данные, полученные из 1442 Карточка ФЛ. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate birthDate;

    /** Данные, полученные из 1442 Карточка ФЛ. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String birthPlace;

    /** Данные, полученные из 1442 Карточка ФЛ. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean nonResident;

    /** Данные, полученные из 1442 Карточка ФЛ. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastNameLatin;

    /** Данные, полученные из 1442 Карточка ФЛ. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstNameLatin;

    /** Данные, полученные из 1442 Карточка ФЛ. */
    private List<DocumentDto> documents;
    private List<AddressDto> addresses;
    private List<PhoneDto> phones;
    private List<MatchResult> matchResults;

}
