package com.example.tlover.domain.diary.exception;

import org.springframework.http.HttpStatus;

import static com.example.tlover.domain.diary.exception.DiaryExceptionList.*;

public class AlreadyExistDiaryException extends DiaryException{
    public AlreadyExistDiaryException() {
        super(
                ALREADY_EXIST_DIARY.getCODE(),
                ALREADY_EXIST_DIARY.getHttpStatus(),
                ALREADY_EXIST_DIARY.getMESSAGE()
        );
    }
}
