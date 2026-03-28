package com.example.weather.service;

import com.example.weather.config.Config;
import com.example.weather.model.WeatherResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class WeatherService {

    private final GeocodingClient geoClient;
    private final WeatherClient weatherClient;

    public WeatherService(Config config) {
        this.geoClient = new GeocodingClient(config);
        this.weatherClient = new WeatherClient(config);
    }

    /*public Optional<WeatherResponse> getWeatherForCity(String city) {
        Optional<double[]> coords = geoClient.geocode(city);

        if (coords.isEmpty()) return Optional.empty();

        double lat = coords.get()[0];
        double lon = coords.get()[1];

        return weatherClient.fetch(lat, lon);
    }*/

    public Optional<WeatherResponse> getWeatherForCity(String city) {
        Optional<double[]> coords = geoClient.geocode(city);

        if (coords.isEmpty()) return Optional.empty();

        double lat = coords.get()[0];
        double lon = coords.get()[1];

        WeatherResponse response = weatherClient.getFiveDayForecast(lat, lon);

        if (response == null || response.daily == null) {
            return Optional.empty();
        }

        response.daily.weatherDescription = new ArrayList<>();
        response.daily.weatherIcon = new ArrayList<>();
        for (int code : response.daily.weatherCode) {
            response.daily.weatherDescription.add(getWeatherDescription(code));
            response.daily.weatherIcon.add(getWeatherIcon(code));
        }

        return Optional.of(response);
    }

    public void printFiveDayForecast(String city) {
        try {
            Optional<double[]> coordsOpt = geoClient.geocode(city);
            if (coordsOpt.isEmpty()) {
                System.out.println("❌ Città non trovata.");
                return;
            }

            double[] coords = coordsOpt.get();
            WeatherResponse response = weatherClient.getFiveDayForecast(coords[0], coords[1]);
            response.daily.weatherDescription = new ArrayList<>();

            for (int i = 0; i < response.daily.weatherCode.size(); i++) {
                int code = response.daily.weatherCode.get(i);
                response.daily.weatherDescription.add(getWeatherDescription(code));
            }

            if (response == null || response.daily == null) {
                System.out.println("❌ Dati previsione non disponibili.");
                return;
            }

            System.out.println("\nPrevisioni meteo fino 5 giorni per " + city + ":\n");

            for (int i = 0; i < Math.min(5, response.daily.time.size()); i++) {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEE dd MMM", Locale.ITALIAN);
                LocalDate parsedDate = LocalDate.parse(response.daily.time.get(i), inputFormatter);
                String formattedDate = parsedDate.format(outputFormatter);
                
                System.out.printf(
                    "%-12s | %s | Min: %5.1f°C | Max: %5.1f°C%n",
                    formattedDate,
                    getWeatherDescription(response.daily.weatherCode.get(i)),
                    response.daily.temperatureMin.get(i),
                    response.daily.temperatureMax.get(i)
                );
            }

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private String getWeatherDescription(int code) {
        return switch (code) {
            // Sole e cielo sereno
            case 0 -> "Sole";
            case 1, 2 -> "Parzialmente nuvoloso";
            case 3 -> "Nuvoloso";
            
            // Nebbia
            case 45, 48 -> "Nebbia";
            
            // Pioggia leggera / rovesci
            case 51, 53, 55 -> "Pioggia leggera";
            case 56, 57 -> "Pioggia ghiacciata leggera";
            case 61, 63, 65 -> "Pioggia";
            case 66, 67 -> "Pioggia ghiacciata forte";
            case 80, 81, 82 -> "Rovesci di pioggia";
            
            // Neve
            case 71, 73, 75 -> "Neve";
            case 85, 86 -> "Rovesci di neve";
            case 77 -> "Grandine leggera";
            
            // Temporali
            case 95, 96 -> "Temporale";
            case 99 -> "Temporale con grandine";
            
            // Default
            default -> "Variabile";
        };
    }

    private String getWeatherIcon(int code) {
        return switch (code) {
            case 0 -> "01d"; // Sole
            case 1, 2 -> "02d"; // Parzialmente nuvoloso
            case 3 -> "03d"; // Nuvoloso
            case 45, 48 -> "50d"; // Nebbia
            case 51, 53, 55, 61, 63, 65 -> "10d"; // Pioggia
            case 71, 73, 75, 85, 86 -> "13d"; // Neve
            case 95, 96, 99 -> "11d"; // Temporale
            default -> "02d";
        };
    }

}