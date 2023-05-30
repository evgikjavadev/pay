package ru.vtb.msa.rfrm.integration.arbitration.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.vtb.msa.rfrm.integration.checking.dto.response.RequestResult;

import java.util.List;

@Data
@Builder
public class ArbitrationRequest {

    /** Генерация на стороне нашей СУБО. */
    private String requestId;

    /** Дата и время направления запроса, с таймзоной. */
    private String dateTimeRequest;

    /** Матрица комплаенс (заполняем константу как в примере: 1847_MOBC_1). */
    private String product;

    /** Заполняем константу как в примере: RUB. */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currency;

    /** Заполняем константу как в примере: SELL. */
    private String requestType;

    /** Заполняем константу как в примере: 1847_MOBC. */
    private String fsId;
    private String entryChannel;
    private InfoEmployee infoEmployee;
    private List<DecisionResultsArbitr> decisionResults;
    private RequestResult requestResult;

}
