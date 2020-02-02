package com.hackuci.csbois.service.idm.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackuci.csbois.service.idm.base.Result;
import com.hackuci.csbois.service.idm.core.RetrievalQueries;
import com.hackuci.csbois.service.idm.core.TwilioMessaging;
import com.hackuci.csbois.service.idm.logger.ServiceLogger;
import com.hackuci.csbois.service.idm.model.Data.BuyerRequestModel;
import com.hackuci.csbois.service.idm.model.ResponseModel;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("buy")
public class BuyRequest {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyRequest(@Context HttpHeaders headers, String jsonText)
    {
        ServiceLogger.LOGGER.info("I AM HERE");
        Response.ResponseBuilder builder;
        BuyerRequestModel requestModel;
        ResponseModel responseModel;
        ObjectMapper mapper = new ObjectMapper();
        try{
            requestModel = mapper.readValue(jsonText, BuyerRequestModel.class);
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

        ResultSet buyerRS = RetrievalQueries.getUser(requestModel.getEmail());
        ResultSet swipeRS = RetrievalQueries.getUserBySwipeID(requestModel.getSwipe_id());
        String buyerPhoneNumber = null;
        String sellerEmail = null;
        try{
            if(swipeRS.next())
            {
                sellerEmail = swipeRS.getString("email");
            }
            if(buyerRS.next())
            {
                buyerPhoneNumber = buyerRS.getString("phone_number");
            }

        } catch(SQLException e)
        {
            e.printStackTrace();
        }
        if(sellerEmail == null){
            builder = new ResponseModel(Result.FAILED_TEXT_MESSAGE).getResponse();
            builder.build();
        }
        ResultSet sellerRS = RetrievalQueries.getUser(sellerEmail);
        String sellerPhoneNumber = null;
        try {
            if(sellerRS.next())
            {
                sellerPhoneNumber = sellerRS.getString("phone_number");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        buyerPhoneNumber = "+1" + buyerPhoneNumber;
        sellerPhoneNumber = "+1" + sellerPhoneNumber;
        ServiceLogger.LOGGER.info("from: " + buyerPhoneNumber + " to: " + sellerPhoneNumber);
        TwilioMessaging.firstMeeting(sellerPhoneNumber,buyerPhoneNumber);
        builder = new ResponseModel(Result.SENT_TEXT_MESSAGE).getResponse();
        return builder.build();
    }
}
