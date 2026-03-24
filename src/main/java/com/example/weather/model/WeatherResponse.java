package com.example.weather.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    @JsonProperty("latitude")
    public double latitude;

    @JsonProperty("longitude")
    public double longitude;

    @JsonProperty("current_weather")
    public CurrentWeather currentWeather;

    @JsonProperty("daily")
    public Daily daily;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Daily {

        @JsonProperty("time")
        public List<String> time;

        @JsonProperty("temperature_2m_max")
        public List<Double> temperatureMax;

        @JsonProperty("temperature_2m_min")
        public List<Double> temperatureMin;

        @JsonProperty("weathercode")
        public List<Integer> weatherCode;
    }

}