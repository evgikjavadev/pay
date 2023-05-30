package ru.vtb.msa.rfrm.integration.arbitration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.msa.rfrm.integration.arbitration.dto.enums.DecisionArbitrage;
import ru.vtb.msa.rfrm.integration.arbitration.dto.enums.StageArbitrage;

/** Класс ответа от 1906 Управление арбитражем по сделке. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationArbitr {
    private Integer idApplicationArbtr;
    private StageArbitrage stageArbitrage;
    private DecisionArbitrage decisionArbitrage;
    private String message;
}
