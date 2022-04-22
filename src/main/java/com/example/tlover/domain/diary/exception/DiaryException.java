package com.example.tlover.domain.diary.exception;

import com.example.tlover.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class DiaryException extends ApplicationException {

    protected DiaryException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }


}
