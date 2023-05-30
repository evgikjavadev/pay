package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response;

import lombok.Data;

@Data
public class EmailCommunicationItem{
	private String loginExternalSystem;
	private String nameExternalSystem;
	private String updateDate;
	private String emailType;
	private String endDate;
	private boolean trustFlag;
	private String startDate;
	private String url;
}