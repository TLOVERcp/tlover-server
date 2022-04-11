package com.example.tlover.domain.user.exception.oauth2;

public class KakaoUnAuthorizedFaildException extends RuntimeException {
    public KakaoUnAuthorizedFaildException(int responseCode, String s) {
        super("responseCode : "+responseCode+",  "+s);
    }

}
