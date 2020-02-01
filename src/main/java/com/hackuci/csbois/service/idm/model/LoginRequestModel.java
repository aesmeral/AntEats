package com.hackuci.csbois.service.idm.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequestModel extends RequestModel {
    @JsonProperty(value = "password", required = true)
    private char[] password;

    public LoginRequestModel() {}

    @JsonCreator
    public LoginRequestModel(@JsonProperty(value = "email", required = true) String email,
                             @JsonProperty(value = "password", required = true) char[] password) {
        super(email);
        this.password = password;
    }

    @JsonProperty(value = "password")
    public char[] getPassword() {
        return password;
    }
}
