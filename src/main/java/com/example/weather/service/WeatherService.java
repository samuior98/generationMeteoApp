package com.example.weather.service;

import com.example.weather.config.Config;
import com.example.weather.model.WeatherResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

public class WeatherService {

    private final GeocodingClient geoClient;
    private final WeatherClient weatherClient;

    public WeatherService(Config config) {
        this.geoClient = new GeocodingClient(config);
        this.weatherClient = new WeatherClient(config);
    }

    public Optional<WeatherResponse> getWeatherForCity(String city) {
        Optional<double[]> coords = geoClient.geocode(city);

        if (coords.isEmpty()) return Optional.empty();

        double lat = coords.get()[0];
        double lon = coords.get()[1];

        return weatherClient.fetch(lat, lon);
    }

    public void printFiveDayForecast(String city) {
        try {
            Optional<double[]> coordsOpt = geoClient.geocode(city);
            if (coordsOpt.isEmpty()) {
                System.out.println("Città non trovata.");
                return;
            }

            double[] coords = coordsOpt.get();
            WeatherResponse response = weatherClient.getFiveDayForecast(coords[0], coords[1]);

            if (response == null || response.daily == null) {
                System.out.println("Dati previsione non disponibili.");
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
            case 0 -> "☀️ Sole";
            case 1, 2 -> "⛅ Parzialmente nuvoloso";
            case 3 -> "☁️ Nuvoloso";
            case 45, 48 -> "🌫️ Nebbia";
            case 51, 53, 55 -> "🌦️ Pioggia leggera";
            case 61, 63, 65 -> "🌧️ Pioggia";
            case 71, 73, 75 -> "❄️ Neve";
            default -> "🌍 Variabile";
        };
    }

}