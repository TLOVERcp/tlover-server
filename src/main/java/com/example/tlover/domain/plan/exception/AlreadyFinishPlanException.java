package com.example.tlover.domain.plan.exception;

public class AlreadyFinishPlanException extends PlanException {
    public AlreadyFinishPlanException() {
        super(PlanExceptionList.ALREADY_FINISH_PLAN.getCODE(),
                PlanExceptionList.ALREADY_FINISH_PLAN.getHttpStatus(),
                PlanExceptionList.ALREADY_FINISH_PLAN.getMESSAGE()
        );
    }
}
