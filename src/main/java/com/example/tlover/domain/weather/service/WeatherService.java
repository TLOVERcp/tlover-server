package com.example.tlover.domain.weather.service;

import com.example.tlover.domain.weather.dto.WeatherResultContext;

public interface WeatherService {
    void saveWeather();
    WeatherResultContext getWeather(String cityCode, WeatherResultContext weatherResultContext);
}
