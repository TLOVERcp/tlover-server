package com.example.tlover.domain.plan.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum planExceptionList {
    NOAUTHORITYUSER("P0001", HttpStatus.FORBIDDEN, "해당 계획에 접근 권한이 없는 유저입니다.");

    private final String CODE;
    private final org.springframework.http.HttpStatus httpStatus;
    private final String MESSAGE;

}
