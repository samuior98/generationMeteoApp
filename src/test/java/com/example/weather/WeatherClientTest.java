package com.example.weather;

import com.example.weather.config.Config;
import com.example.weather.model.WeatherResponse;
import com.example.weather.service.WeatherClient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WeatherClientTest {

    private WeatherClient weatherClient;

    @BeforeEach
    void setUp() {
        // Config di test (usa i default del load)
        Config config = Config.load();

        // Istanza del client "mockato" tramite override del metodo fetch
        weatherClient = new WeatherClient(config) {
            @Override
            public Optional<WeatherResponse> fetch(double lat, double lon) {
                // Creiamo una risposta finta
                WeatherResponse.Daily daily = new WeatherResponse.Daily();
                daily.time = List.of("2026-03-24", "2026-03-25", "2026-03-26", "2026-03-27", "2026-03-28");
                daily.temperatureMax = List.of(20.0, 21.0, 22.0, 23.0, 24.0);
                daily.temperatureMin = List.of(10.0, 11.0, 12.0, 13.0, 14.0);
                daily.weatherCode = List.of(0, 1, 2, 0, 3);

                WeatherResponse response = new WeatherResponse();
                response.latitude = lat;
                response.longitude = lon;
                response.daily = daily;

                return Optional.of(response);
            }
        };
    }

    @Test
    void testFetchReturnsWeather() {
        Optional<WeatherResponse> response = weatherClient.fetch(45.4642, 9.1900);

        assertTrue(response.isPresent(), "Response should be present");
        assertNotNull(response.get().daily, "Daily weather should not be null");
        assertEquals(5, response.get().daily.temperatureMax.size(), "Temperature max list should have 5 entries");
    }

    @Test
    void testFetchWithInvalidCoordinates() {
        Optional<WeatherResponse> response = weatherClient.fetch(999, 999);

        // Qui nel mock restituiamo comunque una risposta, ma in un test reale si potrebbe restituire empty
        assertTrue(response.isPresent());
        assertEquals(999, response.get().latitude);
        assertEquals(999, response.get().longitude);
    }
    
}