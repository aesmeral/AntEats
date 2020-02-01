package com.hackuci.csbois.service.idm.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackuci.csbois.service.idm.base.Result;
import com.hackuci.csbois.service.idm.core.RetrievalQueries;
import com.hackuci.csbois.service.idm.logger.ServiceLogger;
import com.hackuci.csbois.service.idm.model.LoginRequestModel;
import com.hackuci.csbois.service.idm.model.RequestModel;
import com.hackuci.csbois.service.idm.model.ResponseModel;
import com.hackuci.csbois.service.idm.core.EmailPasswordHelper;
import com.hackuci.csbois.service.idm.security.Crypto;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

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
import java.sql.ResultSet;
import java.sql.SQLException;

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
            else if (!passwordsMatch(email, password)) {
                ServiceLogger.LOGGER.warning("Password is incorrect.");
                responseModel = new ResponseModel(Result.INCORRECT_PASSWORD);
                return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
            }

            responseModel = new ResponseModel(Result.LOGIN_SUCCESSFUL);
            ServiceLogger.LOGGER.info(responseModel.getMessage());
            return responseModel.buildResponse();
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
            return null;
        }
    }

    private boolean passwordsMatch(String email, char[] givenPassword) {
        try {
            ResultSet rs = RetrievalQueries.getUser(email);
            if(rs.next()) {
                byte[] password_salt = Hex.decodeHex(rs.getString("password_salt"));
                byte[] hashed_password = Crypto.hashPassword(givenPassword, password_salt, Crypto.ITERATIONS, Crypto.KEY_LENGTH);

                String encodedGivenPassword = Hex.encodeHexString(hashed_password);
                String password_from_db = rs.getString("password");

                return encodedGivenPassword.equals(password_from_db);
            }
            else {
                return false;
            }
        }
        catch (DecoderException e) {
            e.printStackTrace();
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
