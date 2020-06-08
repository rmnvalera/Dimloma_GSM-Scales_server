package com.valenator.diploma;

import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.jdbi3.strategies.DefaultNameStrategy;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.valenator.diploma.controllers.MessagesController;
import com.valenator.diploma.controllers.PingController;
import com.valenator.diploma.liquibase.NameableMigrationsBundle;
import com.valenator.diploma.storage.Beehivesdb;
import com.valenator.diploma.storage.FaultTolerantDatabase;
import com.valenator.diploma.util.Constants;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

public class GSMScales extends Application<GSMScalesConfiguretion> {

    @Override
    public void initialize(Bootstrap<GSMScalesConfiguretion> bootstrap) {
        bootstrap.setObjectMapper(Jackson.newMinimalObjectMapper().registerModule(new Jdk8Module()));

        bootstrap.addBundle(new NameableMigrationsBundle<GSMScalesConfiguretion>("database", "scalesdb.xml") {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(GSMScalesConfiguretion configuration) {
                return configuration.getScalesDatabase();
            }
        });
    }

    @Override
    public String getName() {
        return "GSM-Scales";
    }

    public void run(GSMScalesConfiguretion config, Environment environment) {

        SharedMetricRegistries.add(Constants.METRICS_NAME, environment.metrics());
        environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        environment.getObjectMapper().setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        environment.getObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        JdbiFactory jdbiFactory = new JdbiFactory(DefaultNameStrategy.CHECK_EMPTY);
        Jdbi profileJdbi = jdbiFactory.build(environment, config.getScalesDatabase(), "database");
        FaultTolerantDatabase database = new FaultTolerantDatabase("database", profileJdbi, config.getScalesDatabase().getCircuitBreakerConfiguration());

        Beehivesdb beehivesdb = new Beehivesdb(database);

        environment.jersey().register(new MessagesController(beehivesdb));
        environment.jersey().register(new PingController());
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
    }


    public static void main(String[] args) throws Exception {
        new GSMScales().run(args);
    }
}
