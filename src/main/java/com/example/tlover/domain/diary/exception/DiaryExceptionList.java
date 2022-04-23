package com.example.tlover.domain.diary.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DiaryExceptionList {
    SUCCESS("D0000",HttpStatus.OK, "요청에 성공하였습니다."),
    NO_SUCH_ELEMENT("D0001", HttpStatus.NOT_FOUND,"인자를 찾을 수 없습니다.");

    private final String CODE;
    private final HttpStatus httpStatus;
    private final String MESSAGE;
}
