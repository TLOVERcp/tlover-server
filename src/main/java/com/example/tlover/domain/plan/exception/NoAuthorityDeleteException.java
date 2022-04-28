package com.example.tlover.domain.plan.exception;

public class NoAuthorityDeleteException extends planException {
    public NoAuthorityDeleteException() {
        super(planExceptionList.NO_AUTHORITY_DELETE.getCODE(),
                planExceptionList.NO_AUTHORITY_DELETE.getHttpStatus(),
                planExceptionList.NO_AUTHORITY_DELETE.getMESSAGE()
        );
    }
}
