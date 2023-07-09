package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response;

import lombok.Data;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.PersonAccounts;

import java.util.List;

@Data
public class MessageResponse{
	private List<PersonAccounts> personAccounts;
}