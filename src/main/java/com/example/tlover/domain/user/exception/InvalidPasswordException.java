package com.example.tlover.domain.user.exception;

public class InvalidPasswordException  extends RuntimeException{
    public InvalidPasswordException(String s) {
        super(s);
    }
}

