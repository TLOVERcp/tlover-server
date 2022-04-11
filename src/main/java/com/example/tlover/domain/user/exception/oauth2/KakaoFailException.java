package com.example.tlover.domain.user.exception.oauth2;

public class KakaoFailException extends RuntimeException{
    public KakaoFailException(int responseCode, String s) {
        super("responseCode : "+responseCode+",  "+s);
    }
}
