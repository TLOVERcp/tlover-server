package com.example.tlover.domain.thema.exception;

import com.example.tlover.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class ThemaException extends ApplicationException {

    protected ThemaException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
