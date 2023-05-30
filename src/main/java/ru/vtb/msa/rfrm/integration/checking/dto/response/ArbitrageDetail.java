package ru.vtb.msa.rfrm.integration.checking.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArbitrageDetail {

    /**
     * ID запроса SAS AML.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String requestSasAmlId;

    /**
     * Дата и время принятого решения.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime decisionDate;

    /**
     * Срок действия арбитража в днях.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer decisionDuration;
}
