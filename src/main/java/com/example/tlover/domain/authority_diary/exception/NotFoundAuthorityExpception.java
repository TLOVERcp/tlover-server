package com.example.tlover.domain.authority_diary.exception;

public class NotFoundAuthorityExpception extends AuthorityDiaryException{

    public NotFoundAuthorityExpception() {
        super(
                AuthorityDiaryExceptionList.NOT_FOUND_AUTHORITY.getCODE(),
                AuthorityDiaryExceptionList.NOT_FOUND_AUTHORITY.getHttpStatus(),
                AuthorityDiaryExceptionList.NOT_FOUND_AUTHORITY.getMESSAGE()
        );
    }
}
