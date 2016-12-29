package com.prashast.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/rest")
public class SecuredRest {

    @GET
    @Path("/getName")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUser(@Context HttpServletRequest request){
        return request.getUserPrincipal().getName();
    }
}
