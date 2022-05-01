package com.example.tlover.domain.plan.exception;

public class DeniedShareAcceptException extends PlanException {
        public DeniedShareAcceptException() {
                super(PlanExceptionList.DENIED_SHARE_ACCEPT.getCODE(),
                        PlanExceptionList.DENIED_SHARE_ACCEPT.getHttpStatus(),
                        PlanExceptionList.DENIED_SHARE_ACCEPT.getMESSAGE()
                );
        }

}
