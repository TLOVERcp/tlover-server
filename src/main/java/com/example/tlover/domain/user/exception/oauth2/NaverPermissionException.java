package com.example.tlover.domain.user.exception.oauth2;

import com.example.tlover.domain.user.constant.UserConstants.ENaverExceptionMessage;

public class NaverPermissionException extends RuntimeException{
    public NaverPermissionException() {super(ENaverExceptionMessage.eNaverPermissionExceptionMessage.getValue());}
}
