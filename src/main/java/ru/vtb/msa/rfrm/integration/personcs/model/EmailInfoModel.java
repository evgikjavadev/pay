package ru.vtb.msa.rfrm.integration.personcs.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailInfoModel {
    /**
     * Адрес электронной почты.
     */
    private String url;


    private String uRI;

    /**
     * Код типа Email.
     */
    private String emailType;
}
