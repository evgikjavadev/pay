package ru.vtb.msa.rfrm.integration.personaccounts.client.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
public class AccountInfoRequest {
	private List<String> productTypes;
}