package com.example.tlover.domain.user.exception.oauth2.kakao;

import com.example.tlover.domain.user.exception.UserException;

public class KakaoUrlException extends UserException {
    public KakaoUrlException(String s) {
        super(KakaoUserExceptionList.FAILED_URL_KAKAO.getCODE(),
                KakaoUserExceptionList.FAILED_URL_KAKAO.getHttpStatus(),
                KakaoUserExceptionList.FAILED_URL_KAKAO.getMESSAGE()+s);
    }
}
