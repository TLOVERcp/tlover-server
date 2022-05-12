package com.example.tlover.domain.weather.dto;


import com.example.tlover.domain.weather.entity.Weather;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "날씨 결과값을 받기 위한 객체")
public class WeatherResultContext {

    private String weatherRegion;

    private String weatherTciGrade;

    private double kmaTci;

    private String time;

    private Long regionId;

    public Weather toEntity(){
        Weather weather = new Weather();
        weather.setWeatherRegion(this.weatherRegion);
        weather.setWeatherTciGrade(this.weatherTciGrade);
        weather.setKmaTci(this.kmaTci);
        weather.setTime(this.time);
        return weather;
    }
}
