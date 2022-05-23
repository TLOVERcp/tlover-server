package com.example.tlover.domain.diary.exception;

import org.springframework.http.HttpStatus;

import static com.example.tlover.domain.diary.exception.DiaryExceptionList.NO_AUTHORITY_MODIFY;

public class NoAuthorityModifyException extends DiaryException{
    public NoAuthorityModifyException() {
        super(NO_AUTHORITY_MODIFY.getCODE(), NO_AUTHORITY_MODIFY.getHttpStatus(), NO_AUTHORITY_MODIFY.getMESSAGE());
    }
}
