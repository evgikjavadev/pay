package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountInfoRequest {

	//private HeaderRequest headerRequest;
	//private MessageRequest productTypes;

	private List<String> productTypes;
}