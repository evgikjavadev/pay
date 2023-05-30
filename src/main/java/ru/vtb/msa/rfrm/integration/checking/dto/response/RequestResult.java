package ru.vtb.msa.rfrm.integration.checking.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.msa.rfrm.integration.checking.dto.ArbitrageType;
import ru.vtb.msa.rfrm.integration.checking.dto.DecisionType;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestResult {

    /**
     * Решение по проверке (суммарное по стратегии): ALLOW, DENY, ARBITRATION.
     */
    private DecisionType decision;

    /**
     * Текстовый комментарий (Суммарно decisionResults/ comment).
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String comment;

    private List<ArbitrageType> arbitrageTypes;

}
