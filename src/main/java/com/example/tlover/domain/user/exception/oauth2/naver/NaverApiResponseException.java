package com.example.tlover.domain.user.exception.oauth2.naver;

import com.example.tlover.domain.user.exception.UserException;

public class NaverApiResponseException extends UserException {
    public NaverApiResponseException() {
        super(NaverUserExceptionList.NAVER_API_RESPONSE.getCODE(),
                NaverUserExceptionList.NAVER_API_RESPONSE.getHttpStatus(),
                NaverUserExceptionList.NAVER_API_RESPONSE.getMESSAGE()
        );
    }
}
