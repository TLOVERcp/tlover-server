package com.example.tlover.domain.user.exception;

public class NotCertifiedValueException extends UserException{
    public NotCertifiedValueException() {
        super(UserExceptionList.NOT_CERTIFIED.getCODE(),
                UserExceptionList.NOT_CERTIFIED.getHttpStatus(),
                UserExceptionList.NOT_CERTIFIED.getMESSAGE()
        );
    }
}

