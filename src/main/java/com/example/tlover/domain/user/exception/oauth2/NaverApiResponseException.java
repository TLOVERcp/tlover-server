package com.example.tlover.domain.user.exception.oauth2;

public class NaverApiResponseException extends RuntimeException{
    public NaverApiResponseException(String s) {
        super(s);
    }
}
