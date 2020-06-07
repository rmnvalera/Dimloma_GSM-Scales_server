package com.valenator.diploma;

import com.codahale.metrics.SharedMetricRegistries;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.valenator.diploma.controllers.PingController;
import com.valenator.diploma.util.Constants;
import io.dropwizard.Application;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.security.InvalidKeyException;

public class GSMScales extends Application<GSMScalesConfiguretion> {

    @Override
    public void initialize(Bootstrap<GSMScalesConfiguretion> bootstrap) {
        bootstrap.setObjectMapper(Jackson.newMinimalObjectMapper().registerModule(new Jdk8Module()));

    }

    @Override
    public String getName() {
        return "GSM-Scales";
    }

    public void run(GSMScalesConfiguretion config, Environment environment) throws InvalidKeyException {

        SharedMetricRegistries.add(Constants.METRICS_NAME, environment.metrics());
        environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        environment.getObjectMapper().setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        environment.getObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        environment.jersey().register(new PingController());
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
    }


    public static void main(String[] args) throws Exception {
        new GSMScales().run(args);
    }
}
