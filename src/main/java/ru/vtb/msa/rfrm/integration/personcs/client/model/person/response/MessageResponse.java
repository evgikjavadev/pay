package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response;

import lombok.Data;

import java.util.List;

@Data
public class MessageResponse{
	private List<PersonItem> person;
}