package com.example.tlover.domain.user.exception;

public class UserNicknameDuplicateException extends UserException {
    public UserNicknameDuplicateException() {
        super(UserExceptionList.USER_NICKNAME_DUPLICATE.getCODE(),
                UserExceptionList.USER_NICKNAME_DUPLICATE.getHttpStatus(),
                UserExceptionList.USER_NICKNAME_DUPLICATE.getMESSAGE()
        );
    }
}
