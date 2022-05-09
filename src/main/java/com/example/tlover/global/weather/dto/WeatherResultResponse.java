package com.example.tlover.global.weather.dto;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "날씨 결과값을 받기 위한 객체")
public class WeatherResultResponse {
    private String courseName;
}
