package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetPersonRequest {
//    /**
//     * Идентификатор системы.
//     */
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private String externalSystemId;

    /**
     * Клиент.
     */
    private List<ContactRequestModel> person;
}
