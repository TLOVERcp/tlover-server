package com.example.tlover.global.jwt.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtExceptionList {
    NOTFOUNDJWT("J0001", HttpStatus.NOT_FOUND, "JWT 토큰이 비어있습니다."),
    EXPIRE_REFRESHTOKEN("J0002", HttpStatus.FORBIDDEN, "REFRESH-TOKEN이 만료되었습니다."),
    EXPIRE_ACCESSTOKEN("J0003", HttpStatus.FORBIDDEN, "ACCESS-TOKEN이 만료되었습니다.");

    private final String CODE;
    private final org.springframework.http.HttpStatus httpStatus;
    private final String MESSAGE;

}
