package com.example.tlover.domain.plan.exception;

public class NotFoundAuthorityPlanException extends PlanException {
        public NotFoundAuthorityPlanException() {
                super(PlanExceptionList.NOT_FOUND_AUTHORITYPLAN.getCODE(),
                        PlanExceptionList.NOT_FOUND_AUTHORITYPLAN.getHttpStatus(),
                        PlanExceptionList.NOT_FOUND_AUTHORITYPLAN.getMESSAGE()
                );
        }

}
