package ru.vtb.msa.rfrm.integration.arbitration.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.vtb.msa.rfrm.integration.checking.dto.DecisionType;
import ru.vtb.msa.rfrm.integration.checking.dto.ServiceType;
import ru.vtb.msa.rfrm.integration.checking.dto.response.ArbitrageDetail;

import java.util.List;

@Data
@Builder
public class DecisionResultsArbitr {
    private ServiceType serviceType;
    private DecisionType decision;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String comment;
    private List<SubjectDetailsList> subjectDetailsList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArbitrageDetail arbitrageDetails;

}
