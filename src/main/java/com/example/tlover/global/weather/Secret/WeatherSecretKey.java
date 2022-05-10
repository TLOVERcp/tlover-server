package com.example.tlover.global.weather.Secret;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherSecretKey {

    public static String WEATHER_SERVICE_KEY;

    @Value("${weather.service-key}")
    private void setWeatherServiceKey(String WEATHER_SERVICE_KEY){
        this.WEATHER_SERVICE_KEY = WEATHER_SERVICE_KEY;
    }

}
