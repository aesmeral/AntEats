package com.hackuci.csbois.service.idm.base;

import javax.ws.rs.core.Response.Status;

public enum Result
{
    JSON_PARSE_EXCEPTION   (-3, "JSON Parse Exception.",   Status.BAD_REQUEST),
    JSON_MAPPING_EXCEPTION (-2, "JSON Mapping Exception.", Status.BAD_REQUEST),

    INTERNAL_SERVER_ERROR  (-1, "Internal Server Error.",  Status.INTERNAL_SERVER_ERROR),

    FOUND_USER             (10, "User found.",             Status.OK),
    NO_USER_FOUND          (11, "Could not find user.",    Status.OK),

    PASSWORD_INVALID_LENGTH (-12, "Password has invalid length.", Status.BAD_REQUEST),
    EMAIL_INVALID_FORMAT    (-11, "Email address has invalid format.", Status.BAD_REQUEST),
    EMAIL_INVALID_LENGTH    (-10, "Email address has invalid length.", Status.BAD_REQUEST),

    PASSWORD_TOO_SHORT_OR_LONG (12, "Password does not meet length requirements.", Status.OK),
    PASSWORD_CHARACTER_REQ     (13, "Password does not meet character requirements.", Status.OK),
    EMAIL_ALREADY_EXISTS       (16, "Email already in use.", Status.OK);

    private final int    resultCode;
    private final String message;
    private final Status status;

    Result(int resultCode, String message, Status status)
    {
        this.resultCode = resultCode;
        this.message = message;
        this.status = status;
    }

    public int getResultCode()
    {
        return resultCode;
    }

    public String getMessage()
    {
        return message;
    }

    public Status getStatus()
    {
        return status;
    }
}