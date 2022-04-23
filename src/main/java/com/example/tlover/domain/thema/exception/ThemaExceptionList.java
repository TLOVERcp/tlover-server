package com.example.tlover.domain.thema.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ThemaExceptionList {

    NOT_FOUND_THEMA_NAME("T0001",HttpStatus.FORBIDDEN, "해당 테마 이름을 찾지 못했습니다.");

    private final String CODE;
    private final org.springframework.http.HttpStatus httpStatus;
    private final String MESSAGE;

}
