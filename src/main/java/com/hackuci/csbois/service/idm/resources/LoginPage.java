package com.hackuci.csbois.service.idm.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackuci.csbois.service.idm.base.Result;
import com.hackuci.csbois.service.idm.logger.ServiceLogger;
import com.hackuci.csbois.service.idm.model.LoginRequestModel;
import com.hackuci.csbois.service.idm.model.RequestModel;
import com.hackuci.csbois.service.idm.model.ResponseModel;
import com.hackuci.csbois.service.idm.core.EmailPasswordHelper;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;

import static com.hackuci.csbois.service.idm.core.EmailPasswordHelper.*;


@Path("login")
public class LoginPage {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Response login(@Context HttpHeaders headers, String jsonText) {
        LoginRequestModel requestModel;
        ResponseModel responseModel;
        ObjectMapper mapper = new ObjectMapper();

        try {
            requestModel = mapper.readValue(jsonText, LoginRequestModel.class);
            ServiceLogger.LOGGER.info("Received [POST] login request.");
            ServiceLogger.LOGGER.info("Request:\n" + jsonText);

            String email = requestModel.getEmail();
            char[] password = requestModel.getPassword();

            if (!isValidEmailLength(email)) {
                ServiceLogger.LOGGER.warning("Email address has invalid length.");
                responseModel = new ResponseModel(Result.EMAIL_INVALID_LENGTH);
                return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
            }
            else if (!isValidPasswordLength(password)) {
                ServiceLogger.LOGGER.warning("Password has invalid length.");
                responseModel = new ResponseModel(Result.PASSWORD_INVALID_LENGTH);
                return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
            }
            else if (!isValidEmailFormat(email)) {
                ServiceLogger.LOGGER.warning("Email address has invalid format.");
                responseModel = new ResponseModel(Result.EMAIL_INVALID_FORMAT);
                return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
            }
            else if (!userFound(email)) {
                ServiceLogger.LOGGER.warning("User not found.");
                responseModel = new ResponseModel(Result.NO_USER_FOUND);
                return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
            }
            else if (!passwordsMatch(password)) {
                ServiceLogger.LOGGER.warning("Password is incorrect.");
                responseModel = new ResponseModel(Result.INCORRECT_PASSWORD);
                return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
            }
        }
        catch (IOException e) {
            if (e instanceof JsonParseException) {
                ServiceLogger.LOGGER.warning("JSON Parse Exception.");
                responseModel = new ResponseModel(Result.JSON_PARSE_EXCEPTION);
                return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
            }
            else if (e instanceof JsonMappingException) {
                ServiceLogger.LOGGER.warning("JSON Mapping exception.");
                responseModel = new ResponseModel((Result.JSON_MAPPING_EXCEPTION));
                return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
            }
        }
    }
}
