package com.example.tlover.domain.user.exception.oauth2;

import com.example.tlover.domain.user.constant.UserConstants.ENaverExceptionMessage;

public class NaverApiResponseException extends RuntimeException{
    public NaverApiResponseException() {
        super(ENaverExceptionMessage.eNaverApiResponseExceptionMessage.getValue());
    }
}
