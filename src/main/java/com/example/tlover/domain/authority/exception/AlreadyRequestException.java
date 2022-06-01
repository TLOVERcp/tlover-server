package com.example.tlover.domain.authority.exception;

public class AlreadyRequestException extends AuthorityDiaryException{

    public AlreadyRequestException() {
        super(
                AuthorityDiaryExceptionList.ALREADY_ACCEPT.getCODE(),
                AuthorityDiaryExceptionList.ALREADY_ACCEPT.getHttpStatus(),
                AuthorityDiaryExceptionList.ALREADY_ACCEPT.getMESSAGE()
                );
    }

}
