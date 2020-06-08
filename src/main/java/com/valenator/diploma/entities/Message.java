package com.valenator.diploma.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    @JsonProperty("dht_1_H")
    private double dhtHumidity1;

    @JsonProperty("dht_2_H")
    private double dhtHumidity2;

    @JsonProperty("dht_1_T")
    private double dhtTemperature1;

    @JsonProperty("dht_2_T")
    private double dhtTemperature2;

    @JsonProperty
    private double weight;

    @JsonProperty
    private double balance;

    public double getDhtHumidity1() {
        return dhtHumidity1;
    }

    public double getDhtHumidity2() {
        return dhtHumidity2;
    }

    public double getDhtTemperature2() {
        return dhtTemperature2;
    }

    public double getDhtTemperature1() {
        return dhtTemperature1;
    }

    public double getWeight() {
        return weight;
    }

    public double getBalance() {
        return balance;
    }
}
