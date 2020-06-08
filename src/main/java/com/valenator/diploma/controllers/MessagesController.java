package com.valenator.diploma.controllers;

import com.valenator.diploma.entities.Message;
import com.valenator.diploma.storage.Beehivesdb;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/messages/")
public class MessagesController {

    private final Beehivesdb beehivesdb;

    public MessagesController(Beehivesdb beehivesdb) {
        this.beehivesdb = beehivesdb;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response ping(@Valid Message message) {

        beehivesdb.create(message);

        return Response.ok().build();
    }
}
