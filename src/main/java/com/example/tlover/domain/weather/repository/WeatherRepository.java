package com.example.tlover.domain.weather.repository;

import com.example.tlover.domain.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, String>{
    Optional<Weather> findByWeatherRegion(String weatherRegion);
}
