package ru.vtb.msa.rfrm.integration.personaccounts.client.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Response<R> {
    private HttpHeaders headers;

    private CommonResponseAccounts<R> body;
}
