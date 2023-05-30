package ru.vtb.msa.rfrm.integration.checking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDetail {

    /**
     * Является основным субъектом или связанным лицом. Значения:
     * return true - для основного субъекта / false - для связанного субъекта
     */
    private boolean main;

    /**
     * Уникальный идентификатор объекта проверки в формате UUID.
     */
    private String subjectId;

    /**
     * ID клиента во фронтальной системе.
     */
    private String fsClientId;

    /**
     * Тип клиента.
     */
    private String subjectType;

    /**
     * Тип сущности.
     */
    private String entityType;

    /**
     * Тип связи.
     */
    private String roleType;

    /**
     * Для ИП, но пусть будет
     */
    private String name;

    /**
     * Фамилия.
     */
    private String lastName;

    /**
     * Имя.
     */
    private String firstName;

    /**
     * Отчество.
     */
    private String patronymic;

    /**
     * Принятое решение по субъекту.
     */
    private String decision;

    /**
     * Комментарий к принятому решению по субъекту.
     */
    private String comment;

    /**
     * Признак необходимости арбитража.
     */
    private boolean arbitration;

    /**
     * Код стратегии принятия решения. Актуально для ответа от сервиса "Принятие решения МСЛ".
     */
    private String strategy;

    /**
     * Детали решения по ЧС или СЛ (фигурант).
     */
    private List<MatchResult> matchResults;
}
