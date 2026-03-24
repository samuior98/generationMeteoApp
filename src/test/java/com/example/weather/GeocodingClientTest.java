package com.example.weather;

import com.example.weather.config.Config;
import com.example.weather.service.GeocodingClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GeocodingClientTest {

    private GeocodingClient geocodingClient;

    @BeforeEach
    void setUp() {
        Config config = Config.load();

        // Creiamo un GeocodingClient "mockato" tramite override del metodo geocode
        geocodingClient = new GeocodingClient(config) {
            @Override
            public Optional<double[]> geocode(String city) {
                // Risposte finte
                if ("Milano".equalsIgnoreCase(city)) {
                    return Optional.of(new double[]{45.4642, 9.1900});
                } else if ("Roma".equalsIgnoreCase(city)) {
                    return Optional.of(new double[]{41.9028, 12.4964});
                } else {
                    return Optional.empty();
                }
            }
        };
    }

    @Test
    void testGeocodeValidCity() {
        Optional<double[]> coords = geocodingClient.geocode("Milano");

        assertTrue(coords.isPresent(), "Coordinates should be present");
        assertEquals(45.4642, coords.get()[0], 0.0001);
        assertEquals(9.1900, coords.get()[1], 0.0001);
    }

    @Test
    void testGeocodeAnotherValidCity() {
        Optional<double[]> coords = geocodingClient.geocode("Roma");

        assertTrue(coords.isPresent());
        assertEquals(41.9028, coords.get()[0], 0.0001);
        assertEquals(12.4964, coords.get()[1], 0.0001);
    }

    @Test
    void testGeocodeInvalidCity() {
        Optional<double[]> coords = geocodingClient.geocode("CittàInesistente");

        assertTrue(coords.isEmpty(), "Coordinates should be empty for unknown city");
    }
    
}