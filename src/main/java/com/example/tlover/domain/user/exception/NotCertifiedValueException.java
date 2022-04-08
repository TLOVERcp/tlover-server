package com.example.tlover.domain.user.exception;

public class NotCertifiedValueException extends RuntimeException{
    public NotCertifiedValueException() {
        super(UserExceptionMessage.NOT_CERTIFIED_EXCEPTION_MASSAGE.getMessage());
    }
}

