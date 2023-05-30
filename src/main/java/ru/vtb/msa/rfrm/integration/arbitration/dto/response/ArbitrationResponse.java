package ru.vtb.msa.rfrm.integration.arbitration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArbitrationResponse {
    private ApplicationArbitr applicationArbtr;
}
