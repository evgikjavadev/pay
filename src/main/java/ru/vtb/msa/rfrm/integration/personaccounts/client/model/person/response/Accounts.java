package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonDeserialize(builder = Accounts.AccountsBuilder.class)
public class Accounts {
    private String accounts;
}
