package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Email клиента.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailInfo {

    /**
     * Адрес электронной почты.
     */
    @JsonProperty("url")
    private String url;

    @JsonProperty("uRI")
    private String uRI;

    /**
     * Код типа Email.
     */
    private String emailType;

    /**
     * Наименование системы источника в которой производились изменения.
     */
    private String nameExternalSystem;

    /**
     * Флаг доверительной системы.
     */
    private Boolean trustFlag;

    /**
     * Дата начала действия.
     */
    private LocalDateTime startDate;

    /**
     * Дата окончания действия.
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
