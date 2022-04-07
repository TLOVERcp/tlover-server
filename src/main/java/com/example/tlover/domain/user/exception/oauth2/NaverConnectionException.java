package com.example.tlover.domain.user.exception.oauth2;
import com.example.tlover.domain.user.constant.UserConstants.ENaverExceptionMessage;
public class NaverConnectionException extends RuntimeException{
    public NaverConnectionException() {super(ENaverExceptionMessage.eNaverConnectionExceptionMessage.getValue());}
}
