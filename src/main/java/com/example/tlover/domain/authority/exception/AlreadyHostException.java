package com.example.tlover.domain.authority.exception;

import static com.example.tlover.domain.authority.exception.AuthorityDiaryExceptionList.ALREADY_HOST;

public class AlreadyHostException extends AuthorityDiaryException{

    public AlreadyHostException() {
        super(
                ALREADY_HOST.getCODE(),
                ALREADY_HOST.getHttpStatus(),
                ALREADY_HOST.getMESSAGE()
        );
    }
}
