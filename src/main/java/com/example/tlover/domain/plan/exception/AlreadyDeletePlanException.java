package com.example.tlover.domain.plan.exception;

public class AlreadyDeletePlanException extends planException {
    public AlreadyDeletePlanException() {
        super(planExceptionList.ALREADY_DELETE_PLAN.getCODE(),
                planExceptionList.ALREADY_DELETE_PLAN.getHttpStatus(),
                planExceptionList.ALREADY_DELETE_PLAN.getMESSAGE()
        );
    }
}
