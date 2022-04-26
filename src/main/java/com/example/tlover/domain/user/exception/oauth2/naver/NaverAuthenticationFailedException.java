package com.example.tlover.domain.user.exception.oauth2.naver;

import com.example.tlover.domain.user.exception.UserException;

public class NaverAuthenticationFailedException extends UserException {
    public NaverAuthenticationFailedException() {
        super(NaverUserExceptionList.NAVER_AUTHENTIVATION_FAILED.getCODE(),
                NaverUserExceptionList.NAVER_AUTHENTIVATION_FAILED.getHttpStatus(),
                NaverUserExceptionList.NAVER_AUTHENTIVATION_FAILED.getMESSAGE()
        );
    }
}
