package com.example.tlover.domain.authority_diary.exception;

import com.example.tlover.domain.diary.exception.DiaryExceptionList;

import static com.example.tlover.domain.authority_diary.exception.AuthorityDiaryExceptionList.*;

public class OnlyRequestException extends AuthorityDiaryException{

    public OnlyRequestException() {
        super(
                ONLY_REQUEST.getCODE(),
                ONLY_REQUEST.getHttpStatus(),
                ONLY_REQUEST.getMESSAGE()
        );
    }

}
