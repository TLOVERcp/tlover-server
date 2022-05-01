package com.example.tlover.domain.plan.exception;

public class NoAuthorityPlanException extends PlanException {
        public NoAuthorityPlanException() {
                super(PlanExceptionList.NO_AUTHORITY_PLAN.getCODE(),
                        PlanExceptionList.NO_AUTHORITY_PLAN.getHttpStatus(),
                        PlanExceptionList.NO_AUTHORITY_PLAN.getMESSAGE()
                );
        }

}
