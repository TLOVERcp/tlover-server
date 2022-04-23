package com.example.tlover.domain.user.exception;

public class PasswordEqualException extends UserException {
    public PasswordEqualException() {
        super(UserExceptionList.PASSWORD_EQUAL.getCODE(),
                UserExceptionList.PASSWORD_EQUAL.getHttpStatus(),
                UserExceptionList.PASSWORD_EQUAL.getMESSAGE()
        );
    }
}

