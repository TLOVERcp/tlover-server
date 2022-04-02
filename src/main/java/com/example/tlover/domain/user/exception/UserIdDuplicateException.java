package com.example.tlover.domain.user.exception;

public class UserIdDuplicateException extends RuntimeException{
    public UserIdDuplicateException() {
        super(UserExceptionMessage.USER_ID_DUPLICATE_EXCEPTION_MASSAGE.getMessage());
    }
}
