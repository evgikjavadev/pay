//package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.Data;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Data
//public class PersonItem{
//	private List<PersonDocumentIdentity> personDocumentIdentity;
//	private String lastName;
//	@JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
//	private LocalDateTime updateDate;
//	private String previousLastName;
//	private List<Object> education;
//	private List<ContactPhoneCommunicationItem> contactPhoneCommunication;
//	private String bankEmployee;
//	private String partyUId;
//	private String pubOfficialStatus;
//	private List<Object> contactRelationship;
//	private String type;
//	private String dependentNumber;
//	private boolean taxRezident;
//	private String birthPlace;
//	private String fullNameGenitive;
//	private String maritalStatusCode;
//	private String previousFirstName;
//	private boolean consentCreditBureauUpload;
//	private List<Object> segment;
//	private String tin;
//	@JsonFormat(pattern = "MM/dd/yyyy")
//	private LocalDate deathDateTime;
//	@JsonFormat(pattern = "MM/dd/yyyy")
//	private LocalDate birthDateTime;
//	private boolean terminated;
//	private boolean rezidentFlag;
//	private String employerCode;
//	private List<Address> address;
//	private String nationalityCountryCode;
//	private String inn;
//	private String fullNameAblative;
//	@JsonFormat(pattern = "MM/dd/yyyy")
//	private String changeDateFullName;
//	private String childrenNumber;
//	private List<Object> employment;
//	private List<Object> relatives;
//	private String lastNameLat;
//	private String firstNameLat;
//	private String firstName;
//	private String loginExternalSystem;
//	private String nameExternalSystem;
//	private String genderCode;
//	private String fullNameDative;
//	private String householderNumber;
//	private String middleName;
//	private List<EmailCommunicationItem> emailCommunication;
//	private String numTaxInspection;
//	private boolean consentCreditBureauVerification;
//	@JsonFormat(pattern = "MM/dd/yyyy")
//	private String startDate;
//	private String status;
//	private String previousMiddleName;
//}