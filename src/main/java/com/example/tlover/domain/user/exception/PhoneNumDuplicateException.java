package com.example.tlover.domain.user.exception;

public class PhoneNumDuplicateException extends RuntimeException{
    public PhoneNumDuplicateException() {
        super(UserExceptionMessage.PHONE_NUM_DUPLICATE_EXCEPTION_MASSAGE.getMessage());
    }
}
