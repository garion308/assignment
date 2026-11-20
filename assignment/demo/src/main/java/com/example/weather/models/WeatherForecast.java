package com.example.weather.models;

public class WeatherForecast {
    private String area;
    private String forecast;

    public WeatherForecast(String area, String forecast) {
        this.area = area;
        this.forecast = forecast;
    }

    public String getArea() {
        return area;
    }

    public String getForecast() {
        return forecast;
    }
}