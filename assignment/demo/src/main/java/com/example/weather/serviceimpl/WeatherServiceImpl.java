package com.example.weather.serviceimpl;

import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestClient;

import com.example.weather.models.WeatherForecast;
import com.example.weather.service.WeatherService;

import tools.jackson.databind.JsonNode;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService{


    private final RestClient restClient;

    private volatile List<WeatherForecast> forecastList = List.of();

    public WeatherServiceImpl() {
        this.restClient = RestClient.builder()
            .baseUrl("https://api-open.data.gov.sg")
            .build();
    }

    @PostConstruct
    public void initialize() {
        getWeatherForecast();
    }


    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void scheduledRefresh() {
        getWeatherForecast();
        System.out.println("refreshed weather forecasts");
    }

    public synchronized void getWeatherForecast() {

        try {
            JsonNode response = restClient.get()
                .uri("/v2/real-time/api/two-hr-forecast")
                .retrieve()
                .body(JsonNode.class);

            if (response != null) {
                JsonNode currentForecasts = response.path("data")
                .path("items")
                .path(0)
                .path("forecasts");

                List<WeatherForecast> updatedForecasts = new ArrayList<>();

                for (JsonNode item : currentForecasts) {
                    String area = item.path("area").asString();
                    String forecast = item.path("forecast").asString();

                    updatedForecasts.add(
                        new WeatherForecast(area, forecast)
                    );
                }

                forecastList = List.copyOf(updatedForecasts);

            }

        } catch (Exception e) {
            System.err.println(
                "Failed to get weather forcasts: " + e.getMessage()
            );
        }
    }

    public List<WeatherForecast> getForecasts() {
        return forecastList;
    }

    public String getForecastByArea(String area) {
        return forecastList.stream()
            .filter(f -> f.getArea().equalsIgnoreCase(area))
            .map(WeatherForecast::getForecast)
            .findFirst()
            .orElse("Forecast not available");
    }
}