package com.example.tlover.domain.plan.exception;

public class NotFoundPlanException extends planException {
        public NotFoundPlanException() {
                super(planExceptionList.NOT_FOUND_PLAN.getCODE(),
                        planExceptionList.NOT_FOUND_PLAN.getHttpStatus(),
                        planExceptionList.NOT_FOUND_PLAN.getMESSAGE()
                );
        }

}
