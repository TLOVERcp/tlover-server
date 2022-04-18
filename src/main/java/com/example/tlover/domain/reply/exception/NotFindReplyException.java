package com.example.tlover.domain.reply.exception;

import com.example.tlover.domain.user.exception.UserExceptionMessage;

public class NotFindReplyException extends RuntimeException{
    public NotFindReplyException() {
        super(UserExceptionMessage.DENIED_ACCESS_EXCEPTION_MASSAGE.getMessage());
    }
}

