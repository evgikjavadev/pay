package ru.vtb.msa.rfrm.integration.checking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestInfo {

    /**
     * ID запроса.
     */
    private String requestId;

    /**
     * ID запроса, на которое дается ответ.
     */
    private String receivedRequestId;
}
