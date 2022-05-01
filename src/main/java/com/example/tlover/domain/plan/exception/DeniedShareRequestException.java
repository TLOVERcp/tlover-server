package com.example.tlover.domain.plan.exception;

public class DeniedShareRequestException extends PlanException {
        public DeniedShareRequestException() {
                super(PlanExceptionList.DENIED_SHARE_REQUEST.getCODE(),
                        PlanExceptionList.DENIED_SHARE_REQUEST.getHttpStatus(),
                        PlanExceptionList.DENIED_SHARE_REQUEST.getMESSAGE()
                );
        }

}
