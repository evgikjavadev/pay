package ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.closephoneinputmodel;

import lombok.Data;

@Data
public class ClosePhoneFullRequest {
    private ClosePhoneHeaderRequest headerRequest;
    private ClosePhoneMessageRequest messageRequest;
}
