package com.valenator.diploma.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/ping/")
public class PingController {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String ping() {
        return "Pong";
    }
}
