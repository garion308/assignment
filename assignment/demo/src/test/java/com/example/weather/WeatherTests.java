package com.example.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.weather.models.WeatherForecast;
import com.example.weather.service.WeatherService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest 
@AutoConfigureMockMvc
public class WeatherTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherService weatherService;

    @Test
    void testGetWeather() throws Exception {

       when(weatherService.getForecasts()).thenReturn(
            Arrays.asList(
                new WeatherForecast("Bedok", "Partly Cloudy"),
                new WeatherForecast("Tampines", "Cloudy")
            )
        );

        mockMvc.perform(get("/weather"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].area").value("Bedok"))
               .andExpect(jsonPath("$[0].forecast").value("Partly Cloudy"))
               .andExpect(jsonPath("$[1].area").value("Tampines"))
               .andExpect(jsonPath("$[1].forecast").value("Cloudy"));
    }

    @Test
    void testGetWeatherByArea() throws Exception {
        when(weatherService.getForecastByArea("Bedok"))
                           .thenReturn("Partly Cloudy (Day)");

        mockMvc.perform(get("/weather").param("area", "Bedok"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.area").value("Bedok"));
    }

    @Test
    void testGetWeatherByAreaNotFound() throws Exception {

        mockMvc.perform(get("/weather").param("area", "Narnia"))
               .andExpect(status().isNotFound());
    }
}
