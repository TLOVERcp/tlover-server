package com.example.tlover.domain.user.exception.oauth2.naver;

import com.example.tlover.domain.user.exception.UserException;

public class NaverPermissionException extends UserException {
    public NaverPermissionException() {
        super(NaverUserExceptionList.NAVER_PERMISSION.getCODE(),
                NaverUserExceptionList.NAVER_PERMISSION.getHttpStatus(),
                NaverUserExceptionList.NAVER_PERMISSION.getMESSAGE()
        );
    }
}
