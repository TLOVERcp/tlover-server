package com.example.tlover.domain.user.exception;

public class UserIdDuplicateException extends UserException {
    public UserIdDuplicateException() {
        super(UserExceptionList.USER_ID_DUPLICATE.getCODE(),
                UserExceptionList.USER_ID_DUPLICATE.getHttpStatus(),
                UserExceptionList.USER_ID_DUPLICATE.getMESSAGE()
        );
    }
}
