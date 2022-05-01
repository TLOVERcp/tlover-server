package com.example.tlover.domain.plan.exception;

public class NoAuthorityShareException extends PlanException {
    public NoAuthorityShareException() {
        super(PlanExceptionList.NO_AUTHORITY_SHARE.getCODE(),
                PlanExceptionList.NO_AUTHORITY_SHARE.getHttpStatus(),
                PlanExceptionList.NO_AUTHORITY_SHARE.getMESSAGE()
        );
    }
}
