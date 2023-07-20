package ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//        "4-MASTER_ACCOUNT-40817810001006010797",
//        "4-CURRENT_ACCOUNT-40817810101006012239"
//})
//@Generated("jsonschema2pojo")
public class Accounts {

    private Map<String, Object> map;

    @JsonProperty("4-MASTER_ACCOUNT-40817810001006010797")
    @Valid
    public ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response._4MasterAccount40817810001006010797 _4MasterAccount40817810001006010797;
    @JsonProperty("4-CURRENT_ACCOUNT-40817810101006012239")
    @Valid
    public ru.vtb.msa.rfrm.integration.personaccounts.client.model.person.response._4CurrentAccount40817810101006012239 _4CurrentAccount40817810101006012239;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
