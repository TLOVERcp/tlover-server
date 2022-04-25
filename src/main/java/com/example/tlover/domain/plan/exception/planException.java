package com.example.tlover.domain.plan.exception;

import com.example.tlover.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class planException extends ApplicationException {

    protected planException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
