package com.example.tlover.domain.user.exception.oauth2;

public class NaverNotFoundException extends RuntimeException{
    public NaverNotFoundException(String s) {
        super(s);
    }
}