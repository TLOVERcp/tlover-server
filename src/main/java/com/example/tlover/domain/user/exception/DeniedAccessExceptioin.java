package com.example.tlover.domain.user.exception;

public class DeniedAccessExceptioin extends RuntimeException{
    public DeniedAccessExceptioin() {
        super(UserExceptionMessage.DENIED_ACCESS_EXCEPTION_MASSAGE.getMessage());
    }
}

