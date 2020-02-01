package com.hackuci.csbois.service.idm.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequestModel extends LoginRequestModel{
    @JsonProperty(value = "phoneNumber", required = true)
    private char[] phoneNumber;

    @JsonCreator
    public RegisterRequestModel(@JsonProperty(value = "email", required = true) String email,
                                @JsonProperty(value = "password", required = true) char[] password,
                                @JsonProperty(value = "phoneNumber", required = true) char[] phoneNumber) {
        super(email, password);
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty(value = "phoneNumber")
    public char[] getPhoneNumber() {
        return phoneNumber;
    }
}
