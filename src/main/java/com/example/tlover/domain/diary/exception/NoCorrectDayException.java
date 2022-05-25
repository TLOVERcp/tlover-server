package com.example.tlover.domain.diary.exception;

import static com.example.tlover.domain.diary.exception.DiaryExceptionList.*;
public class NoCorrectDayException extends DiaryException{
    public NoCorrectDayException() {
        super(
                NO_CORRECT_DAY.getCODE(),
                NO_CORRECT_DAY.getHttpStatus(),
                NO_CORRECT_DAY.getMESSAGE()
        );
    }
}
