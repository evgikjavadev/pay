package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = ResponseCommon.ResponseCommonBuilder.class)
public class ResponseCommon {
	//private MessageResponse messageResponse;

	@JsonProperty("accounts")
	private Accounts accounts;


}