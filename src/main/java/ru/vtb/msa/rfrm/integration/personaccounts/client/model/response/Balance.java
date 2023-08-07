//package ru.vtb.msa.rfrm.integration.personaccounts.client.model.response;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//import javax.annotation.Generated;
//import javax.validation.Valid;
//import com.fasterxml.jackson.annotation.JsonAnyGetter;
//import com.fasterxml.jackson.annotation.JsonAnySetter;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//        "currency",
//        "amount"
//})
//@Generated("jsonschema2pojo")
//public class Balance {
//
//    @JsonProperty("currency")
//    public String currency;
//    @JsonProperty("amount")
//    public Double amount;
//    @JsonIgnore
//    @Valid
//    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
//
//    @JsonAnyGetter
//    public Map<String, Object> getAdditionalProperties() {
//        return this.additionalProperties;
//    }
//
//    @JsonAnySetter
//    public void setAdditionalProperty(String name, Object value) {
//        this.additionalProperties.put(name, value);
//    }
//
//}
