package com.example.tlover.domain.myfile.exception;

import com.example.tlover.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class MyFileException extends ApplicationException {
    protected MyFileException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
