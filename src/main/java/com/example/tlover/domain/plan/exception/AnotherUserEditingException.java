package com.example.tlover.domain.plan.exception;

public class AnotherUserEditingException extends PlanException {
    public AnotherUserEditingException() {
        super(PlanExceptionList.ANOTHER_USER_EDITING.getCODE(),
                PlanExceptionList.ANOTHER_USER_EDITING.getHttpStatus(),
                PlanExceptionList.ANOTHER_USER_EDITING.getMESSAGE()
        );
    }
}


