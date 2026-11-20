package com.example.weather.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.weather.models.WeatherForecast;
import com.example.weather.service.WeatherService;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public List<WeatherForecast> getForecasts() {
        return weatherService.getForecasts();
    }

    @GetMapping(params = "area")
    public ResponseEntity<Object> getForecastByArea(@RequestParam String area) {
        String forecast = weatherService.getForecastByArea(area);
        if (forecast == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new WeatherForecast(area, forecast));
    }
}