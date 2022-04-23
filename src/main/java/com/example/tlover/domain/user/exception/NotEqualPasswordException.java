package com.example.tlover.domain.user.exception;

public class NotEqualPasswordException extends UserException{
    public NotEqualPasswordException() {
        super(UserExceptionList.NOT_EQUAL_PASSWORD.getCODE(),
                UserExceptionList.NOT_EQUAL_PASSWORD.getHttpStatus(),
                UserExceptionList.NOT_EQUAL_PASSWORD.getMESSAGE()
        );
    }
}

