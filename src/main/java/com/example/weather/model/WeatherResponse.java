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

        @JsonProperty("weatherDescription")
        public List<String> weatherDescription;

        @JsonProperty("weatherIcon")
        public List<String> weatherIcon;

        @JsonProperty("wind_speed_10m_max")
        public List<Double> windSpeedMax;

        @JsonProperty("relative_humidity_2m_max")
        public List<Integer> humidityMax;

        @JsonProperty("weatherDescription")
        public List<String> getWeatherDescription() {
            return weatherDescription;
        }

        @JsonProperty("temperatureMax")
        public List<Double> getTemperatureMax() {
            return temperatureMax;
        }

        @JsonProperty("temperatureMin")
        public List<Double> getTemperatureMin() {
            return temperatureMin;
        }

        @JsonProperty("weatherCode")
        public List<Integer> getWeatherCode() {
            return weatherCode;
        }
    }

}