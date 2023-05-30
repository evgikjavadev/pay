package ru.vtb.msa.rfrm.integration.checking.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ComplexCheckRequest {
    /**
     * Информация о запросе.
     */
    private HeaderInfo headerInfo;

    /**
     * Информация об обслуживании. Обязательный в случае, если decisionServiceTypes включает COMPLIANCE
     */
    private ServiceInfo serviceInfo;

    /**
     * Информация о субъекте проверки.
     */
    private List<SubjectInfo> subjectInfoList;
}
