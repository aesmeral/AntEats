package com.hackuci.csbois.service.idm.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackuci.csbois.service.idm.base.Result;
import com.hackuci.csbois.service.idm.core.EmailPasswordHelper;
import com.hackuci.csbois.service.idm.core.TwilioMessaging;
import com.hackuci.csbois.service.idm.core.UserRecords;
import com.hackuci.csbois.service.idm.logger.ServiceLogger;
import com.hackuci.csbois.service.idm.model.RegisterRequestModel;
import com.hackuci.csbois.service.idm.model.ResponseModel;
import com.hackuci.csbois.service.idm.security.Crypto;
import org.apache.commons.codec.binary.Hex;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;



@Path("register")
public class RegisterPage {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerResponse(@Context HttpHeaders headers, String jsonText) {
        RegisterRequestModel requestModel;
        ResponseModel responseModel;
        ObjectMapper mapper = new ObjectMapper();

        try {
            requestModel = mapper.readValue(jsonText, RegisterRequestModel.class);
        }
        catch (IOException e) {
            e.printStackTrace();

            // resultCode = -3; 400 Bad request; "JSON Parse Exception."
            if(e instanceof JsonParseException) {
                responseModel = new ResponseModel(Result.JSON_PARSE_EXCEPTION);
            }

            // resultCode = -2; 400 Bad request; "JSON Mapping Exception."
            else if (e instanceof JsonMappingException) {
                responseModel = new ResponseModel(Result.JSON_MAPPING_EXCEPTION);
            }

            // resultCode = -1; 500 Internal server error; "Internal Server Error."
            else {
                responseModel = new ResponseModel(Result.INTERNAL_SERVER_ERROR);
            }

            return responseModel.buildResponse();
        }

        ServiceLogger.LOGGER.info("Received register request");
        ServiceLogger.LOGGER.info("Request:\n" + jsonText);

        // resultCode = -12; 400 Bad request; "Password has invalid length."
        if(requestModel.getPassword() == null || requestModel.getPassword().length == 0) {
            responseModel = new ResponseModel(Result.PASSWORD_INVALID_LENGTH);
            ServiceLogger.LOGGER.warning(responseModel.getMessage());
            return responseModel.buildResponse();
        }

        // resultCode = -10; 400 Bad request; "Email address has invalid length."
        if(requestModel.getEmail() == null || requestModel.getEmail().length() == 0) {
            responseModel = new ResponseModel(Result.EMAIL_INVALID_LENGTH);
            ServiceLogger.LOGGER.warning(responseModel.getMessage());
            return responseModel.buildResponse();
        }

        // resultCode = -11; 400 Bad request; "Email address has invalid format."
        if(!EmailPasswordHelper.isValidEmailFormat(requestModel.getEmail())) {
            responseModel = new ResponseModel(Result.EMAIL_INVALID_FORMAT);
            ServiceLogger.LOGGER.warning(responseModel.getMessage());
            return responseModel.buildResponse();
        }

        // resultCode = 12; 200 OK; "Password does not meet length requirements."
        if(!EmailPasswordHelper.isValidPasswordLength(requestModel.getPassword())) {
            responseModel = new ResponseModel(Result.PASSWORD_TOO_SHORT_OR_LONG);
            ServiceLogger.LOGGER.warning(responseModel.getMessage());
            return responseModel.buildResponse();
        }

        // resultCode = 13; 200 OK; "Password does not meet character requirements."
        if(!EmailPasswordHelper.isValidPasswordContents(requestModel.getPassword())) {
            responseModel = new ResponseModel(Result.PASSWORD_CHARACTER_REQ);
            ServiceLogger.LOGGER.warning(responseModel.getMessage());
            return responseModel.buildResponse();
        }

        // resultCode = 16; 200 OK; "Email already in use."
        if(EmailPasswordHelper.userFound(requestModel.getEmail())) {
            responseModel = new ResponseModel(Result.EMAIL_ALREADY_EXISTS);
            ServiceLogger.LOGGER.warning(responseModel.getMessage());
            return responseModel.buildResponse();
        }

        // resultCode = 110; 200 OK; "User registered successfully."
        // --------------------Salt & hash password----------------------
        // Generate random salt
        byte[] salt = Crypto.genSalt();

        // Use salt to hash password
        char[] password = requestModel.getPassword();
        byte[] hashedPassword = Crypto.hashPassword(password, salt, Crypto.ITERATIONS, Crypto.KEY_LENGTH);

        // Encode salt & password; these values are stored
        String encodedSaltPW = Hex.encodeHexString(salt);
        String encodedPassword = Hex.encodeHexString(hashedPassword);

        // Salt and hash phone_number too

        // --------------------------------------------------------------
        // decided not to hash the phone number; its not that much of a sensitive data.

        // Insert new user into user table of database
        TwilioMessaging.testMessage("+16267588643");
        UserRecords.insertIntoUser(requestModel.getEmail(), encodedPassword, encodedSaltPW, new String(requestModel.getPhoneNumber()), null);
        responseModel = new ResponseModel(Result.USER_REGISTERED_SUCCESSFULLY);
        ServiceLogger.LOGGER.info(responseModel.getMessage());
        return responseModel.buildResponse();
    }
}
