package com.example.weather.controller;

import com.example.weather.config.Config;
import com.example.weather.model.WeatherResponse;
import com.example.weather.service.GeocodingClient;
import com.example.weather.service.WeatherClient;
import com.example.weather.service.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WeatherController {

    private final WeatherClient weatherClient;
    private final GeocodingClient geocodingClient;
    private final WeatherService weatherService;

    public WeatherController() {
        Config config = Config.load();
        this.weatherClient = new WeatherClient(config);
        this.geocodingClient = new GeocodingClient(config);
        this.weatherService = new WeatherService(config);
    }

    @GetMapping("/weather")
    public WeatherResponse getWeather(@RequestParam String city) {
        return weatherService.getWeatherForCity(city)
                .orElseThrow(() -> new RuntimeException("City not found"));
    }

}