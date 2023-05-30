package ru.vtb.msa.rfrm.integration.checking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.msa.rfrm.integration.checking.dto.DecisionType;
import ru.vtb.msa.rfrm.integration.checking.dto.ServiceType;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DecisionResults {

    /**
     * Тип сервиса: COMPLIANCE – для ОС Комплаенс, SL_DECISION - для ИС Принятие.
     */
    private ServiceType serviceType;

    /**
     * Решение по проверке.
     */
    private DecisionType decision;

    /**
     * Текстовый комментарий (сообщение сотруднику или клиенту).
     */
    private String comment;

    /**
     * Детали решения.
     */
    private List<SubjectDetail> subjectDetailsList;

    /**
     * Детали арбитража.
     */
    private ArbitrageDetail arbitrageDetails;
}
