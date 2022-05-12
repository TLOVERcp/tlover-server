package com.example.tlover.domain.rating.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RatingExceptionList {
    NOT_FOUND_RATING("r0001", HttpStatus.NOT_FOUND, "해당 등급을 찾을 수 없습니다."),
    ;

    private final String CODE;
    private final HttpStatus httpStatus;
    private final String MESSAGE;

}
