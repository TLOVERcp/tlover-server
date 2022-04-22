package com.example.tlover.domain.reply.exception;

import com.example.tlover.domain.user.exception.UserExceptionMessage;

public class NotFindReplyException extends RuntimeException{
    public NotFindReplyException() {
        super(ReplyExceptionMessage.NOT_FIND_REPLY_EXCEPTION_MESSAGE.getMessage());
    }
}

