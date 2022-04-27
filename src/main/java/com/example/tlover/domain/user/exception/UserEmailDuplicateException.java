package com.example.tlover.domain.user.exception;

public class UserEmailDuplicateException extends UserException {
    public UserEmailDuplicateException() {
        super(UserExceptionList.USER_EMAIL_DUPLICATE.getCODE(),
                UserExceptionList.USER_EMAIL_DUPLICATE.getHttpStatus(),
                UserExceptionList.USER_EMAIL_DUPLICATE.getMESSAGE()
        );
    }
}
