package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = Response.ResponseBuilder.class)
public class Response {
	//private MessageResponse messageResponse;

	@JsonProperty("accounts")
	private Accounts accounts;
}