package com.example.tlover.domain.diary.exception;

import com.example.tlover.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

import static com.example.tlover.domain.diary.exception.DiaryExceptionList.*;

public class NoSuchElementException extends DiaryException {

    private static final String CODE = "D0001";
    private static final String MESSAGE = "인자를 찾을수 없습니다.";

    public NoSuchElementException() {
        super(NO_SUCH_ELEMENT.getCODE(),
                NO_SUCH_ELEMENT.getHttpStatus(),
                NO_SUCH_ELEMENT.getMESSAGE());
    }
}
