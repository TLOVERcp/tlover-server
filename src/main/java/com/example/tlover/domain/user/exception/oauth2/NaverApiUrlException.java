package com.example.tlover.domain.user.exception.oauth2;

import com.example.tlover.domain.user.constant.UserConstants.ENaverExceptionMessage;

public class NaverApiUrlException extends RuntimeException{
    public NaverApiUrlException() {
        super(ENaverExceptionMessage.eNaverApiUrlExceptionMessage.getValue());
    }
}
