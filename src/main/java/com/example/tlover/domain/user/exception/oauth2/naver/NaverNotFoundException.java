package com.example.tlover.domain.user.exception.oauth2.naver;

import com.example.tlover.domain.user.exception.UserException;

public class NaverNotFoundException extends UserException {
    public NaverNotFoundException() {
        super(NaverUserExceptionList.NAVER_NOT_FOUND.getCODE(),
                NaverUserExceptionList.NAVER_NOT_FOUND.getHttpStatus(),
                NaverUserExceptionList.NAVER_NOT_FOUND.getMESSAGE()
        );
    }
}

