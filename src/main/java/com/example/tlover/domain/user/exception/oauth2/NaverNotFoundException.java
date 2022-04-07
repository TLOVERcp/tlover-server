package com.example.tlover.domain.user.exception.oauth2;

import com.example.tlover.domain.user.constant.UserConstants.ENaverExceptionMessage;

public class NaverNotFoundException extends RuntimeException{
    public NaverNotFoundException() {super(ENaverExceptionMessage.eNaverNotFoundExceptionMessage.getValue());}
}
