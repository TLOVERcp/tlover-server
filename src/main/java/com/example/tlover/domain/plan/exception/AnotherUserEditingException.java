package com.example.tlover.domain.plan.exception;

public class AnotherUserEditingException extends planException {
    public AnotherUserEditingException() {
        super(planExceptionList.ANOTHERUSEREDITING.getCODE(),
                planExceptionList.ANOTHERUSEREDITING.getHttpStatus(),
                planExceptionList.ANOTHERUSEREDITING.getMESSAGE()
        );
    }
}


