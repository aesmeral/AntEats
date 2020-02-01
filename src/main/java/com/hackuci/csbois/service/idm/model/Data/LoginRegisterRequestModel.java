package com.hackuci.csbois.service.idm.model.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackuci.csbois.service.idm.model.RequestModel;

public class LoginRegisterRequestModel extends RequestModel {
    @JsonProperty(value = "password", required = true)
    private char[] password;

    @JsonCreator
    public LoginRegisterRequestModel(@JsonProperty(value = "email", required = true) String email,
                                     @JsonProperty(value = "password", required = true) char[] password) {
        super(email);
        this.password = password;
    }

    @JsonProperty(value = "passwoerd")
    public char[] getPassword() {
        return password;
    }
}
