package com.prashast.rest;

import com.prashast.service.SecuredRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Controller
@Path("/")
public class SecuredRest {

    @Autowired
    SecuredRestService securedRestService;

    @GET
    @Path("/getName")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUser(@Context HttpServletRequest request){
        return request.getUserPrincipal().getName();
    }

    @GET
    @Path("/concatenateStrings/{firstName}/{lastName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getStringConcatenated(@PathParam("firstName") String firstName, @PathParam("lastName") String lastName){
        return securedRestService.concatenate(firstName,lastName);
    }
}
