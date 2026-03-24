package com.example.weather.service;

import com.example.weather.config.Config;
import com.example.weather.model.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

public class WeatherClient {

    private final Config config;
    private final HttpClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public WeatherClient(Config config) {
        this.config = config;
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(config.getTimeoutSeconds()))
                .build();
    }

    public Optional<WeatherResponse> fetch(double lat, double lon) {
        try {
            String url = config.getWeatherApiUrl()
                    + "?latitude=" + lat
                    + "&longitude=" + lon
                    + "&current_weather=true";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(config.getTimeoutSeconds()))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) return Optional.empty();

            WeatherResponse weather = mapper.readValue(response.body(), WeatherResponse.class);
            return Optional.of(weather);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public WeatherResponse getFiveDayForecast(double latitude, double longitude) {
        try {
            String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f" +
                "&daily=temperature_2m_max,temperature_2m_min,weathercode&timezone=auto",
                latitude, longitude
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(config.getTimeoutSeconds()))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return null;
            }

            return mapper.readValue(response.body(), WeatherResponse.class);

        } catch (Exception e) {
            return null;
        }
    }

}