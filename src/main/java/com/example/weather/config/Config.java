package com.example.weather.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private final String weatherApiUrl;
    private final String geocodingApiUrl;
    private final int timeoutSeconds;

    private Config(String weatherApiUrl, String geocodingApiUrl, int timeoutSeconds) {
        this.weatherApiUrl = weatherApiUrl;
        this.geocodingApiUrl = geocodingApiUrl;
        this.timeoutSeconds = timeoutSeconds;
    }

    public static Config load() {
        Properties props = new Properties();

        try (InputStream is = Config.class.getResourceAsStream("/application.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            System.out.println("Errore nel caricamento config, uso default");
        }

        String weatherUrl = props.getProperty("weather.api.url", "https://api.open-meteo.com/v1/forecast");
        String geoUrl = props.getProperty("geocoding.api.url", "https://geocoding-api.open-meteo.com/v1/search");
        int timeout = Integer.parseInt(props.getProperty("http.timeout.seconds", "10"));

        return new Config(weatherUrl, geoUrl, timeout);
    }

    public String getWeatherApiUrl() {
        return weatherApiUrl;
    }

    public String getGeocodingApiUrl() {
        return geocodingApiUrl;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }
}