package com.example.tlover.domain.user.exception;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException(String s) {
        super(s);
    }
}
