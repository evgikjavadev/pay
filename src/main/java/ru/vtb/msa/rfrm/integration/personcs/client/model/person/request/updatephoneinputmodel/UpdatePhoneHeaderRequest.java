//package ru.vtb.msa.rfrm.integration.personcs.client.model.person.request.updatephoneinputmodel;
//
//import lombok.Data;
//
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
//@Data
//public class UpdatePhoneHeaderRequest {
//    /**
//     * Идентификатор сообщения
//     */
//    @NotNull
//    @Size(max = 255)
//    private String messageID;
//    /**
//     * Идентификатор системы-потребителя (код РИС системы, которая посылает запрос)
//     */
//    @NotNull
//    @Size(max = 20)
//    private String systemFrom;
//    /**
//     * Идентификатор системы-поставщика (константа "396")
//     */
//    @NotNull
//    @Size(max = 10)
//    private String systemTo;
//    /**
//     * Дата и время формирования сообщения
//     */
//    @NotNull
//    @Size(max = 100)
//    private String creationDateTime;
//    /**
//     * X-CSPC-RISCODE - код РИС
//     */
//    private String xCspcRiscode = "MOBC";
//    /**
//     * X-CSPC-MDMCODE - код системы МДМ ФЛ
//     */
//    private String xCspcMdmCode = "MOBC";
//}
