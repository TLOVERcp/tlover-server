package com.example.tlover.domain.user.exception;

public class PasswordEqualException extends RuntimeException{
    public PasswordEqualException() {
        super(UserExceptionMessage.PASSWORD_EQUAL_EXCEPTION_MASSAGE.getMessage());
    }
}

