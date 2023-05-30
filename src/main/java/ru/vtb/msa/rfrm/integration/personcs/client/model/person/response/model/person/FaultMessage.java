package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.person;

import lombok.Data;

import java.io.Serializable;

@Data
public class FaultMessage implements Serializable {

    /**
     * Статус ошиюки
     */
    private String status;

    /**
     * Сообщение об ошибке
     */
    private String message;
    /**
     * Описание ошибки
     */
    private String description;

    public PersonCsFaultType getStatusType() {
        return PersonCsFaultType.getByStatus(status);
    }
}
