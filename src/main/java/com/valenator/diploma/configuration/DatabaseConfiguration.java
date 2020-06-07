package com.valenator.diploma.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.constraints.NotNull;

public class DatabaseConfiguration extends DataSourceFactory {
    @NotNull
    @JsonProperty
    private CircuitBreakerConfiguration circuitBreaker = new CircuitBreakerConfiguration();

    public CircuitBreakerConfiguration getCircuitBreakerConfiguration() {
        return circuitBreaker;
    }
}
