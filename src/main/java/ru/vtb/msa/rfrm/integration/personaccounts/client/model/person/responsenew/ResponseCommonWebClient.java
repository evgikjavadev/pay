package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.responsenew;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.Accounts;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCommonWebClient {

    //private MessageResponse messageResponse;
    //

    private Object accounts;
}
