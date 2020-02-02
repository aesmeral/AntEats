package com.hackuci.csbois.service.idm.resources;

import com.hackuci.csbois.service.idm.base.Result;
import com.hackuci.csbois.service.idm.core.RetrievalQueries;
import com.hackuci.csbois.service.idm.logger.ServiceLogger;
import com.hackuci.csbois.service.idm.model.Data.SwipeListResponseModel;
import com.hackuci.csbois.service.idm.model.Data.SwipePosting;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("view")
public class ViewSwipes {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewSwipes(@Context HttpHeaders headers,@DefaultValue("10") @QueryParam("limit")int limit)
    {
        String email = headers.getHeaderString("email");
        SwipeListResponseModel responseModel = null;
        Response.ResponseBuilder builder = null;
        ResultSet rs = RetrievalQueries.getSwipes(limit);
        ServiceLogger.LOGGER.info("Data was recieved.");
        ArrayList<SwipePosting> swipesList = new ArrayList<SwipePosting>();
        try {
            while (rs.next()) {
                swipesList.add(new SwipePosting(rs.getString("u.email"), rs.getString("availability"),rs.getFloat("cost"),rs.getString("s.swipe_id")));
            }
        } catch (SQLException e)
        {
            ServiceLogger.LOGGER.info("Something went wrong while retrieving the data");
            e.printStackTrace();
        }
        if(swipesList.isEmpty())
        {
            responseModel = new SwipeListResponseModel(Result.SWIPES_DO_NOT_EXIST);
        }
        else
        {
            responseModel = new SwipeListResponseModel(Result.SWIPES_EXIST);
            SwipePosting [] SwipeData = new SwipePosting[swipesList.size()];
            SwipeData = swipesList.toArray(SwipeData);
            responseModel.setSwipes(SwipeData);
        }
        builder = responseModel.getResponse();
        builder.header("email", "aresmeralda95@gmail.com");
        return builder.build();
    }
}
