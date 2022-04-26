package com.example.tlover.domain.user.exception.oauth2.naver;

import com.example.tlover.domain.user.exception.UserException;

public class NaverConnectionException extends UserException {
    public NaverConnectionException() {
        super(NaverUserExceptionList.NAVER_CONNECTION.getCODE(),
                NaverUserExceptionList.NAVER_CONNECTION.getHttpStatus(),
                NaverUserExceptionList.NAVER_CONNECTION.getMESSAGE()
        );
    }
}
