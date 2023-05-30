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
public class ComplexCheckResponse {

    /**
     * Информация о запросе.
     */
    private RequestInfo requestInfo;

    /**
     * Список принятых решений по субъекту.
     */
    private List<DecisionResults> decisionResults;

    /**
     * Информация о запросе.
     */
    private RequestResult requestResult;
}
