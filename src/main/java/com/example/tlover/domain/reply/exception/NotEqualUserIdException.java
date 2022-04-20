package com.example.tlover.domain.reply.exception;

public class NotEqualUserIdException extends RuntimeException{
    public NotEqualUserIdException() {
        super(ReplyExceptionMessage.NOT_EQUAL_USER_ID_EXCEPTION_MESSAGE.getMessage());
    }
}

