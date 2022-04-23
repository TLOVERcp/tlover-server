package com.example.tlover.domain.user.exception;

public class NotFoundUserException extends UserException {
    public NotFoundUserException() {
        super(UserExceptionList.NOT_FOUND_USER.getCODE(),
                UserExceptionList.NOT_FOUND_USER.getHttpStatus(),
                UserExceptionList.NOT_FOUND_USER.getMESSAGE()
        );
    }
}
