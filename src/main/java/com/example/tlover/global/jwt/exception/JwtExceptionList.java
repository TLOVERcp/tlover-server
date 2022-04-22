package com.example.tlover.global.jwt.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtExceptionList {
    NOTFOUNDJWT("J0001", HttpStatus.FOUND, "JWT 토큰이 비어있습니다.");

    private final String CODE;
    private final org.springframework.http.HttpStatus httpStatus;
    private final String MESSAGE;
//
//    private JwtExceptionList(String CODE, org.springframework.http.HttpStatus httpStatus, String MESSAGE) {
//        this.CODE = CODE;
//        this.httpStatus = httpStatus;
//        this.MESSAGE = MESSAGE;
//    }
}
