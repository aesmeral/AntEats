package com.hackuci.csbois.service.idm.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackuci.csbois.service.idm.base.Result;
import com.hackuci.csbois.service.idm.core.Inserts;
import com.hackuci.csbois.service.idm.core.Util;
import com.hackuci.csbois.service.idm.logger.ServiceLogger;
import com.hackuci.csbois.service.idm.model.Data.SwipePosterRequestModel;
import com.hackuci.csbois.service.idm.model.ResponseModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("post")
public class PostSwipes {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postSwipe(@Context HttpHeaders headers, String jsonText)
    {
        Response.ResponseBuilder builder;
        SwipePosterRequestModel requestModel;
        ResponseModel responseModel;
        ObjectMapper mapper = new ObjectMapper();
        ServiceLogger.LOGGER.info(jsonText);
        try{
            requestModel = mapper.readValue(jsonText, SwipePosterRequestModel.class);
        } catch(IOException e)
        {
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

        ServiceLogger.LOGGER.info("Recieved swipe post request");
        ServiceLogger.LOGGER.info("Request:\n" + jsonText);
        responseModel = new ResponseModel(Result.SWIPE_POST_SUCCESS);
        String swipe_id = Util.generateSwipeID();
        Inserts.insertIntoSwipes(swipe_id,requestModel.getCost(),requestModel.getAvailability());
        Inserts.insertIntoUser_Swipe(swipe_id,requestModel.getEmail());
        builder = responseModel.getResponse();
        return builder.build();
    }
}
