package com.example.tlover.domain.plan.exception;

public class NotFoundPlanException extends PlanException {
        public NotFoundPlanException() {
                super(PlanExceptionList.NOT_FOUND_PLAN.getCODE(),
                        PlanExceptionList.NOT_FOUND_PLAN.getHttpStatus(),
                        PlanExceptionList.NOT_FOUND_PLAN.getMESSAGE()
                );
        }

}
