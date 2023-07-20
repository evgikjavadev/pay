package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.responsenew;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.Accounts;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    private Accounts accounts;
}
