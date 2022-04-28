package com.example.tlover.domain.plan.exception;

public class NoAuthorityPlanException extends planException {
        public NoAuthorityPlanException() {
                super(planExceptionList.NO_AUTHORITY_PLAN.getCODE(),
                        planExceptionList.NO_AUTHORITY_PLAN.getHttpStatus(),
                        planExceptionList.NO_AUTHORITY_PLAN.getMESSAGE()
                );
        }

}
