package com.example.tlover.domain.authority.exception;

import com.example.tlover.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class AuthorityDiaryException extends ApplicationException {
    protected AuthorityDiaryException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
