package com.example.tlover.domain.user.exception.oauth2.kakao;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum KakaoUserExceptionList {
    UNAUTHORIZED_KAKAO("K0001", HttpStatus.UNAUTHORIZED, "카카오 인증에 실패했습니다."),
    FAILED_KAKAO("K0002", HttpStatus.BAD_REQUEST, "카카오 로그인에 실패했습니다."),
    FAILED_URL_KAKAO("K0003", HttpStatus.INTERNAL_SERVER_ERROR ,"카카오 API URL이 잘못되었습니다. : "),
    FAILED_PROTOCOL_KAKAO("K0004", HttpStatus.METHOD_NOT_ALLOWED ,"카카오의 지정된 요청 방식 이외의 프로토콜을 전달했습니다."),
    FAILDE_API_KAKAO("K0005", HttpStatus.INTERNAL_SERVER_ERROR,"카카오 API 응답을 읽는데 실패했습니다.");

    private final String CODE;
    private final org.springframework.http.HttpStatus httpStatus;
    private final String MESSAGE;
}
