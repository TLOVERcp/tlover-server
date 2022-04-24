package com.example.tlover.domain.plan.exception;

public class NoAuthorityUserException extends planException {
        public NoAuthorityUserException() {
                super(planExceptionList.NOAUTHORITYUSER.getCODE(),
                        planExceptionList.NOAUTHORITYUSER.getHttpStatus(),
                        planExceptionList.NOAUTHORITYUSER.getMESSAGE()
                );
        }

}
