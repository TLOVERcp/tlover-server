package com.example.tlover.domain.reply.exception;

public class NotFindReplyException extends ReplyException {
    public NotFindReplyException() {
        super(ReplyExceptionList.NOT_FIND_REPLY.getCODE(),
                ReplyExceptionList.NOT_FIND_REPLY.getHttpStatus(),
                ReplyExceptionList.NOT_FIND_REPLY.getMESSAGE()
        );
    }
}

