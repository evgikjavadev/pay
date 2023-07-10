package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response{
	//private MessageResponse messageResponse;

	@JsonProperty("accounts")
	private Map<String, String> accounts;
}