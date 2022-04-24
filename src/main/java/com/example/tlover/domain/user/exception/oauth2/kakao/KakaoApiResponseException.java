package com.example.tlover.domain.user.exception.oauth2.kakao;

import com.example.tlover.domain.user.exception.UserException;

public class KakaoApiResponseException extends UserException {

    public KakaoApiResponseException() {
        super(KakaoUserExceptionList.FAILDE_API_KAKAO.getCODE(),
                KakaoUserExceptionList.FAILDE_API_KAKAO.getHttpStatus(),
                KakaoUserExceptionList.FAILDE_API_KAKAO.getMESSAGE());
    }
}
