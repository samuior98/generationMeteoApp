package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather {

    @JsonProperty("temperature")
    public double temperature;

    @JsonProperty("windspeed")
    public double windspeed;

    @JsonProperty("weathercode")
    public int weathercode;

    @JsonProperty("time")
    public String time;
}