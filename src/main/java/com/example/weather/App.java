package com.example.weather;

import com.example.weather.config.Config;
import com.example.weather.service.WeatherService;
import com.example.weather.ui.ConsoleUI;

public class App {

    public static void main(String[] args) {
        Config config = Config.load();
        WeatherService service = new WeatherService(config);
        ConsoleUI ui = new ConsoleUI(service);
        ui.run();
    }
    
}