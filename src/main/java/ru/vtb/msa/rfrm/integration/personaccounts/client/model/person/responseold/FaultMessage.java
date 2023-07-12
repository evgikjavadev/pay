package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.responseold;

import lombok.Data;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.responseold.PersonCsFaultType;

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
