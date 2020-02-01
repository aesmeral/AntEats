package com.hackuci.csbois.service.idm.base;

import javax.ws.rs.core.Response.Status;

public enum Result
{
    JSON_PARSE_EXCEPTION   (-3, "JSON Parse Exception.",   Status.BAD_REQUEST),
    JSON_MAPPING_EXCEPTION (-2, "JSON Mapping Exception.", Status.BAD_REQUEST),

    INTERNAL_SERVER_ERROR  (-1, "Internal Server Error.",  Status.INTERNAL_SERVER_ERROR),

    FOUND_USER             (10, "User found.",             Status.OK),
    NO_USER_FOUND          (11, "Could not find user.",    Status.OK);

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