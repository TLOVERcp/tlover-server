package com.example.tlover.domain.user.exception.oauth2.naver;

import com.example.tlover.domain.user.exception.UserException;

public class NaverApiUrlException extends UserException {
    public NaverApiUrlException() {
        super(NaverUserExceptionList.NAVER_API_URL.getCODE(),
                NaverUserExceptionList.NAVER_API_URL.getHttpStatus(),
                NaverUserExceptionList.NAVER_API_URL.getMESSAGE()
        );
    }
}
