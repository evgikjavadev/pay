package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.responseold;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Сегмент.
 */
@Data
public class Segment {

    /**
     * Тип сегмента.
     */
    private String type;

    /**
     * Значение сегмента.
     */
    private String value;

    /**
     * Наименование системы источника в которой производились изменения.
     */
    private String nameExternalSystem;

    /**
     * Дата начала действия.
     */
    private LocalDate startDate;

    /**
     * Дата завершения действия.
     */
    private LocalDateTime endDate;

    /**
     * Дата изменения в системе источнике.
     */
    private LocalDateTime updateDate;

    /**
     * Наименование учетной записи пользователя, производившего изменения в системе источнике.
     */
    private String loginExternalSystem;

    /**
     * Код статуса сегмента.
     */
    private String status;

    /**
     * Критерий.
     */
    private String criterion;
}
