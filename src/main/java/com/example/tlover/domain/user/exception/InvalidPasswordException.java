package com.example.tlover.domain.user.exception;

public class InvalidPasswordException  extends UserException{
    public InvalidPasswordException() {
        super(UserExceptionList.INVALID_PASSWORD.getCODE(),
                UserExceptionList.INVALID_PASSWORD.getHttpStatus(),
                UserExceptionList.INVALID_PASSWORD.getMESSAGE()
        );
    }
}

