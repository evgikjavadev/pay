package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запись в черном списке.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlackListItem {

    /**
     * Тип черного списка.
     */
    private String blackListName;

    /**
     * Значение черного списка.
     */
    private String blackListValue;

    /**
     * Наименование системы источника в которой производились изменения.
     */
    private String nameExternalSystem;

    /**
     * Дата начала действия.
     */
    private LocalDateTime startDate;

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
}
