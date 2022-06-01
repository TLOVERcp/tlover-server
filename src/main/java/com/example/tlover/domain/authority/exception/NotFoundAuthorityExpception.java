package com.example.tlover.domain.authority.exception;

public class NotFoundAuthorityExpception extends AuthorityDiaryException{

    public NotFoundAuthorityExpception() {
        super(
                AuthorityDiaryExceptionList.NOT_FOUND_AUTHORITY.getCODE(),
                AuthorityDiaryExceptionList.NOT_FOUND_AUTHORITY.getHttpStatus(),
                AuthorityDiaryExceptionList.NOT_FOUND_AUTHORITY.getMESSAGE()
        );
    }
}
