package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactRequestModel {
    /**
     * Идентификатор записи во внешней системе.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String id;


    /**
     * Глобальный идентификатор записи Customer HUB.
     * ИД клиента в МДМ (УИК).
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String partyUId;
}
