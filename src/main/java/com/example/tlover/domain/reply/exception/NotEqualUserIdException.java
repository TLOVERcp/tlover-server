package com.example.tlover.domain.reply.exception;

import com.example.tlover.domain.user.exception.UserExceptionList;

public class NotEqualUserIdException extends ReplyException {
    public NotEqualUserIdException() {
        super(ReplyExceptionList.NOT_EQUAL_USER_ID.getCODE(),
                ReplyExceptionList.NOT_EQUAL_USER_ID.getHttpStatus(),
                ReplyExceptionList.NOT_EQUAL_USER_ID.getMESSAGE()
        );
    }
}

