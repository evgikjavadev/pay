package ru.vtb.msa.rfrm.integration.personaccounts.client.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountArray {
    //@JsonProperty("accounts")
    private List<Map<String, Account>> accountsListMap;
}
