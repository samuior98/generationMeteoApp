package com.example.weather.ui;

import com.example.weather.model.WeatherResponse;
import com.example.weather.service.WeatherService;

import java.util.Optional;
import java.util.Scanner;

public class ConsoleUI {

    private final WeatherService service;

    public ConsoleUI(WeatherService service) {
        this.service = service;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Weather App (digita 'exit' per uscire)");

        while (true) {
            System.out.print("> ");
            String city = scanner.nextLine().trim();

            if (city.equalsIgnoreCase("exit")) break;
            if (city.isEmpty()) continue;

            /*Optional<WeatherResponse> result = service.getWeatherForCity(city);

            if (result.isEmpty()) {
                System.out.println("Città non trovata o errore API");
            } else {
                print(result.get(), city);
            }*/

            service.printFiveDayForecast(city);
        }

        System.out.println("Bye!");
    }

    private void print(WeatherResponse wr, String city) {
        System.out.println(city);
        System.out.printf("Temperatura: %.1f°C%n", wr.currentWeather.temperature);
        System.out.printf("Vento: %.1f m/s%n", wr.currentWeather.windspeed);
        System.out.println("-------------------------");
    }
}