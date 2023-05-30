package ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.updatephoneinputmodel;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePhoneShortRequest {
    private UpdatePhoneMessageRequest messageRequest;
}
