package com.example.weather.service;

import com.example.weather.config.Config;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

public class GeocodingClient {

    private final Config config;
    private final HttpClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public GeocodingClient(Config config) {
        this.config = config;
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(config.getTimeoutSeconds()))
                .build();
    }

    public Optional<double[]> geocode(String city) {
        try {
            String url = config.getGeocodingApiUrl()
                    + "?name=" + URLEncoder.encode(city, StandardCharsets.UTF_8)
                    + "&count=1";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(config.getTimeoutSeconds()))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) return Optional.empty();

            JsonNode root = mapper.readTree(response.body());
            JsonNode results = root.get("results");

            if (results == null || results.isEmpty()) return Optional.empty();

            double lat = results.get(0).get("latitude").asDouble();
            double lon = results.get(0).get("longitude").asDouble();

            return Optional.of(new double[]{lat, lon});

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}