package com.example.tlover.domain.plan.exception;

public class AlreadyDeletePlanException extends PlanException {
    public AlreadyDeletePlanException() {
        super(PlanExceptionList.ALREADY_DELETE_PLAN.getCODE(),
                PlanExceptionList.ALREADY_DELETE_PLAN.getHttpStatus(),
                PlanExceptionList.ALREADY_DELETE_PLAN.getMESSAGE()
        );
    }
}
