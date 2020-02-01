package com.hackuci.csbois.service.idm.resources;

import com.hackuci.csbois.service.idm.logger.ServiceLogger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("hello")
public class HelloPage {
    @GET
    public Response hello() {
        System.err.println("Hello!");
        ServiceLogger.LOGGER.info("Hello!");
        return Response.status(Status.OK).build();
    }
}