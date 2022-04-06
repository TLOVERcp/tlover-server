package com.example.tlover.domain.user.exception.oauth2;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class GoogleFailException extends HttpClientErrorException {
    public GoogleFailException(HttpStatus httpStatus , String message) {
        super(httpStatus ,message);
    }
}
