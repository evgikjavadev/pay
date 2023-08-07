//package ru.vtb.msa.rfrm.integration.personaccounts.client.model.response;
//
//import java.util.LinkedHashMap;
//import java.util.List;
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
//        "id",
//        "name",
//        "status",
//        "startDate",
//        "endDate",
//        "system",
//        "userId",
//        "timeStamp",
//        "balance",
//        "miles",
//        "options",
//        "bonusAmount"
//})
//@Generated("jsonschema2pojo")
//public class Multicarta {
//
//    @JsonProperty("id")
//    public String id;
//    @JsonProperty("name")
//    public String name;
//    @JsonProperty("status")
//    public Boolean status;
//    @JsonProperty("startDate")
//    public String startDate;
//    @JsonProperty("endDate")
//    public String endDate;
//    @JsonProperty("system")
//    public String system;
//    @JsonProperty("userId")
//    public String userId;
//    @JsonProperty("timeStamp")
//    public String timeStamp;
//    @JsonProperty("balance")
//    public Integer balance;
//    @JsonProperty("miles")
//    public Integer miles;
//    @JsonProperty("options")
//    @Valid
//    public List<Option> options;
//    @JsonProperty("bonusAmount")
//    public Integer bonusAmount;
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
