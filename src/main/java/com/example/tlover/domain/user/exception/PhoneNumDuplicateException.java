package com.example.tlover.domain.user.exception;

public class PhoneNumDuplicateException extends UserException {
    public PhoneNumDuplicateException() {
        super(UserExceptionList.PHONE_NUM_DUPLICATE.getCODE(),
                UserExceptionList.PHONE_NUM_DUPLICATE.getHttpStatus(),
                UserExceptionList.PHONE_NUM_DUPLICATE.getMESSAGE()
        );
    }
}
