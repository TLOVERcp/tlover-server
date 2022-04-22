package com.example.tlover.domain.user.exception;

public class UserNicknameDuplicateException extends RuntimeException{
    public UserNicknameDuplicateException() {
        super(UserExceptionMessage.USER_NICKNAME_DUPLICATE_EXCEPTION_MASSAGE.getMessage());
    }
}
