//package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.Data;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Data
//public class PersonDocumentIdentity {
//	private String endOfRightToStay;
//	@JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
//	private LocalDateTime updateDate;
//	private String issueName;
//	@JsonFormat(pattern = "MM/dd/yyyy")
//	private LocalDate endDate;
//	private String typeCode;
//	private String number;
//	private String loginExternalSystem;
//	private String nameExternalSystem;
//	private String startOfRightToStay;
//	private String series;
//	private String issueCountryCode;
//	@JsonFormat(pattern = "MM/dd/yyyy")
//	private LocalDate issueDate;
//	private String issueCode;
//	@JsonFormat(pattern = "MM/dd/yyyy")
//	private LocalDate startDate;
//	@JsonFormat(pattern = "MM/dd/yyyy")
//	private LocalDate expirationDate;
//}