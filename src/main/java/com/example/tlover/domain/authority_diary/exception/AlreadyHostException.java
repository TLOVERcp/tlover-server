package com.example.tlover.domain.authority_diary.exception;

import com.example.tlover.domain.diary.exception.DiaryExceptionList;

import static com.example.tlover.domain.authority_diary.exception.AuthorityDiaryExceptionList.ALREADY_HOST;

public class AlreadyHostException extends AuthorityDiaryException{

    public AlreadyHostException() {
        super(
                ALREADY_HOST.getCODE(),
                ALREADY_HOST.getHttpStatus(),
                ALREADY_HOST.getMESSAGE()
        );
    }
}
