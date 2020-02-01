package com.hackuci.csbois.service.idm.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestModel {
    @JsonProperty(value = "email", required = true)
    private String email;

    public RequestModel() {
    }

    @JsonCreator
    public RequestModel(@JsonProperty(value="email",required = true) String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
