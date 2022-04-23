package com.example.tlover.domain.region.exception;

import com.example.tlover.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class RegionException extends ApplicationException {

    protected RegionException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
