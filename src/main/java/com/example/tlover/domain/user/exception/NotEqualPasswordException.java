package com.example.tlover.domain.user.exception;

public class NotEqualPasswordException extends RuntimeException{
    public NotEqualPasswordException() {
        super(UserExceptionMessage.NOT_EQUAL_PASSWORD_MASSAGE.getMessage());
    }
}

