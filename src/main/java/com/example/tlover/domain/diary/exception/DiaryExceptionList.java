package com.example.tlover.domain.diary.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DiaryExceptionList {
    SUCCESS("D0000",HttpStatus.OK, "요청에 성공하였습니다."),
    TEST("D0002",HttpStatus.HTTP_VERSION_NOT_SUPPORTED,"테스트입니다.");

    private final String CODE;
    private final HttpStatus httpStatus;
    private final String MESSAGE;

    private DiaryExceptionList(String CODE, HttpStatus httpStatus, String MESSAGE){
        this.CODE = CODE;
        this.httpStatus = httpStatus;
        this.MESSAGE = MESSAGE;
    }
}
