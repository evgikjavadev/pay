package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response.model.person;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum PersonCsFaultType {
    NOT_FOUND("404"),
    UNKNOWN("");

    private final String status;

    PersonCsFaultType(String status) {
        this.status = status;
    }

    public static PersonCsFaultType getByStatus(String status) {
        return Arrays.stream(values())
                .filter(v -> Objects.equals(v.status, status))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
