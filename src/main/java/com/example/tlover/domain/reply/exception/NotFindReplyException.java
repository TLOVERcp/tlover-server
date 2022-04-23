package com.example.tlover.domain.reply.exception;

public class NotFindReplyException extends RuntimeException{
    public NotFindReplyException() {
        super(ReplyExceptionMessage.NOT_FIND_REPLY_EXCEPTION_MESSAGE.getMessage());
    }
}

