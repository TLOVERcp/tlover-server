package com.example.tlover.domain.plan.exception;

public class AnotherUserEditingException extends planException {
    public AnotherUserEditingException() {
        super(planExceptionList.ANOTHER_USER_EDITING.getCODE(),
                planExceptionList.ANOTHER_USER_EDITING.getHttpStatus(),
                planExceptionList.ANOTHER_USER_EDITING.getMESSAGE()
        );
    }
}


