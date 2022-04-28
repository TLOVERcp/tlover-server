package com.example.tlover.domain.myfile.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MyFileExceptionList {
    NOT_FOUND_MY_FILE("MF0001", HttpStatus.NOT_FOUND, "해당 fileId로 MyFile을 찾을 수 없습니다.");

    private final String CODE;
    private final HttpStatus httpStatus;
    private final String MESSAGE;
}
