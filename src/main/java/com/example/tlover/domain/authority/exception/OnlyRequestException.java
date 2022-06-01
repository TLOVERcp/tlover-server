package com.example.tlover.domain.authority.exception;

import static com.example.tlover.domain.authority.exception.AuthorityDiaryExceptionList.*;

public class OnlyRequestException extends AuthorityDiaryException{

    public OnlyRequestException() {
        super(
                ONLY_REQUEST.getCODE(),
                ONLY_REQUEST.getHttpStatus(),
                ONLY_REQUEST.getMESSAGE()
        );
    }

}
