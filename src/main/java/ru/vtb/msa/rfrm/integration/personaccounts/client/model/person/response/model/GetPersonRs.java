package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPersonRs {
    private GetPersonData messageResponse;
}
