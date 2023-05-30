package ru.vtb.msa.rfrm.integration.checking.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HeaderInfo {

    /**
     * ID запроса в формате UUID.
     */
    private String requestId;

    /**
     * ID фронтальной системы. Должно совпадать с кодом системы и стратегии. Константа 1847_MOBC.
     */
    private String consumerId;

    /**
     * Уникальный идентификатор сессии пользователя (сотрудника офиса), выполняющего операцию (необязательный).
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String sessionId;

    /**
     * IP-адрес клиентской машины, которого выполняется операция. Дефолтное значение 127.0.0.1.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String clientIp;

    /**
     * Типы сервиса принятия решений. Возможные значения: COMPLIANCE; SL_DECISION
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private List<String> decisionServiceTypes;

    /**
     * Код системы из процесса, согласованного в владельцем списка. Константа MOBC_SALES.
     */
    private String processId;
}
