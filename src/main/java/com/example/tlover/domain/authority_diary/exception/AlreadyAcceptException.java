package com.example.tlover.domain.authority_diary.exception;

public class AlreadyAcceptException extends AuthorityDiaryException{
    public AlreadyAcceptException() {
        super(
                AuthorityDiaryExceptionList.ALREADY_ACCEPT.getCODE(),
                AuthorityDiaryExceptionList.ALREADY_ACCEPT.getHttpStatus(),
                AuthorityDiaryExceptionList.ALREADY_ACCEPT.getMESSAGE()
                );
    }
}
