package com.example.tlover.domain.diary.exception;

import static com.example.tlover.domain.diary.exception.DiaryExceptionList.NOT_FOUND_GOOD_WEATHER;

public class NotFoundGoodWeather extends DiaryException{
    public NotFoundGoodWeather(){
        super(
                NOT_FOUND_GOOD_WEATHER.getCODE(),
                NOT_FOUND_GOOD_WEATHER.getHttpStatus(),
                NOT_FOUND_GOOD_WEATHER.getMESSAGE()
        );
    }
}
