package com.example.tlover.domain.diary.exception;

import static com.example.tlover.domain.diary.exception.DiaryExceptionList.*;

public class NotAuthorityDeleteException extends DiaryException{
    public NotAuthorityDeleteException() {
        super(
                NO_AUTHORITY_DELETE.getCODE(),
                NO_AUTHORITY_DELETE.getHttpStatus(),
                NO_AUTHORITY_DELETE.getMESSAGE()
        );

    }
}
