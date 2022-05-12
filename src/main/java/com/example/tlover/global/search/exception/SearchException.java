package com.example.tlover.global.search.exception;

import com.example.tlover.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class SearchException extends ApplicationException {

    protected SearchException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }


}
