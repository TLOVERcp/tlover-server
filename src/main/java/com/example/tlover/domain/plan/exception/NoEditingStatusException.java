package com.example.tlover.domain.plan.exception;

public class NoEditingStatusException extends PlanException {
        public NoEditingStatusException() {
                super(PlanExceptionList.NO_EDITING_STATUS.getCODE(),
                        PlanExceptionList.NO_EDITING_STATUS.getHttpStatus(),
                        PlanExceptionList.NO_EDITING_STATUS.getMESSAGE()
                );
        }

}
