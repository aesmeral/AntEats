package com.hackuci.csbois.service.idm.resources;

import com.hackuci.csbois.service.idm.logger.ServiceLogger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("test")
public class TestPage {
    @Path("hello")
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Response helloWorld() {
        System.err.println("Hello World");
        ServiceLogger.LOGGER.info("Hello!");
        return Response.status(Status.OK).build();
    }
}
