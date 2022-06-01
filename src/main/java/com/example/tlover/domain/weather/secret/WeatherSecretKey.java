package com.example.tlover.domain.weather.secret;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherSecretKey {

    public static String WEATHER_SERVICE_KEY;

    @Value("${weather.service-key}")
    private void setWeatherServiceKey(String WEATHER_SERVICE_KEY){
        this.WEATHER_SERVICE_KEY = WEATHER_SERVICE_KEY;
    }

    public static final String[] MAPKEY = {"body","items","item"};
    public static final String[] AREAKEY = {
                                "1156000000", //서울시
                                "4136000000", //경기도-남양주시
                                "4711100000", //경상북도-포항시
                                "4822000000", //경상남도-통영시
                                "5011000000", //제주도-제주시
                                "4413100000", //충청남도-천안시
                                "4313000000", //충청북도-충주시
                                "4513000000", //전라북도-군산시
                                "4613000000", //전라남도-여수시
                                "4215000000"}; //강원도-강릉시
}
