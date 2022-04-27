package com.example.tlover.domain.user.exception;

public class UserDeletedException extends UserException {
    public UserDeletedException() {
        super(UserExceptionList.USER_DELETED.getCODE(),
                UserExceptionList.USER_DELETED.getHttpStatus(),
                UserExceptionList.USER_DELETED.getMESSAGE()
        );
    }
}
