package ru.vtb.msa.rfrm.integration.personaccounts.client.model.response;

import lombok.Data;

import java.util.Map;

@Data
public class AccountMap {
    private Map<String, Account> obj;
}
