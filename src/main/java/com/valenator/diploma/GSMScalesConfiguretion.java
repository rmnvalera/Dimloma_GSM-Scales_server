package com.valenator.diploma;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.valenator.diploma.configuration.DatabaseConfiguration;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class GSMScalesConfiguretion extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private DatabaseConfiguration scalesDatabase;


    public DatabaseConfiguration getScalesDatabase() {
        return scalesDatabase;
    }
}
