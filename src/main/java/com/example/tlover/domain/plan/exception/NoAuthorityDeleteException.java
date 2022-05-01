package com.example.tlover.domain.plan.exception;

public class NoAuthorityDeleteException extends PlanException {
    public NoAuthorityDeleteException() {
        super(PlanExceptionList.NO_AUTHORITY_DELETE.getCODE(),
                PlanExceptionList.NO_AUTHORITY_DELETE.getHttpStatus(),
                PlanExceptionList.NO_AUTHORITY_DELETE.getMESSAGE()
        );
    }
}
