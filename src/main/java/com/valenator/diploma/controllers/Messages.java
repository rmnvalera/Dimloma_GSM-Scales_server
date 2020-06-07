package com.valenator.diploma.controllers;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/messages/")
public class Messages {


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String ping() {
        return "Pong";
    }
}
