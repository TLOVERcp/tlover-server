package com.example.tlover.domain.user.exception.oauth2.naver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NaverUserExceptionList {
    NAVER_API_RESPONSE("N0001", HttpStatus.CONFLICT, "NAVER API 응답을 읽는데 실패했습니다."),
    NAVER_API_URL("N0002", HttpStatus.CONFLICT, "NAVER API URL이 잘못되었습니다. : "),
    NAVER_AUTHENTIVATION_FAILED("N0003", HttpStatus.UNAUTHORIZED ,"Naver 인증에 실패했습니다."),
    NAVER_CONNECTION("N0004", HttpStatus.CONFLICT ,"NAVER와의 연결이 실패했습니다. : "),
    NAVER_NOT_FOUND("N0005", HttpStatus.NOT_FOUND,"Naver API 검색 결과가 없습니다."),
    NAVER_PERMISSION("N0005", HttpStatus.FORBIDDEN,"Naver API 호출 권한이 없습니다.");

    private final String CODE;
    private final HttpStatus httpStatus;
    private final String MESSAGE;
}
