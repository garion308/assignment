package com.example.weather.service;

import java.util.List;

import com.example.weather.models.WeatherForecast;


public interface WeatherService {
    public List<WeatherForecast> getForecasts();
    public String getForecastByArea(String area);
}