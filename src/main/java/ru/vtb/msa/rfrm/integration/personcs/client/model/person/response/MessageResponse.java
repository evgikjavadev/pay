package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response;

import lombok.Data;
import ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.Person;

import java.util.List;

@Data
public class MessageResponse{
	private List<Person> personAccounts;
}