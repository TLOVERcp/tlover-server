package com.example.tlover.domain.user.exception.oauth2;

import com.example.tlover.domain.user.constant.UserConstants.ENaverExceptionMessage;

public class NaverAuthenticationFailedException extends RuntimeException{
    public NaverAuthenticationFailedException() {super(ENaverExceptionMessage.eNaverAuthenticationFailedExceptionMessage.getValue());}
}
