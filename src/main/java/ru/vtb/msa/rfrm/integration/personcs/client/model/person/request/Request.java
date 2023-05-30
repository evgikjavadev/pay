package ru.vtb.msa.rfrm.integration.personcs.client.model.person.request;

import lombok.Data;

@Data
public class Request {
	private HeaderRequest headerRequest;
	private MessageRequest messageRequest;
}