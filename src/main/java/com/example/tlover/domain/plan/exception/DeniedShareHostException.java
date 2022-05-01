package com.example.tlover.domain.plan.exception;

public class DeniedShareHostException extends PlanException {
        public DeniedShareHostException() {
                super(PlanExceptionList.DENIED_SHARE_HOST.getCODE(),
                        PlanExceptionList.DENIED_SHARE_HOST.getHttpStatus(),
                        PlanExceptionList.DENIED_SHARE_HOST.getMESSAGE()
                );
        }

}
