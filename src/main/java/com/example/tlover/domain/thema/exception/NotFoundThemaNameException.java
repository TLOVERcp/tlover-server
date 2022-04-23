package com.example.tlover.domain.thema.exception;

public class NotFoundThemaNameException extends ThemaException {
    public NotFoundThemaNameException() {
        super(ThemaExceptionList.NOT_FOUND_THEMA_NAME.getCODE(),
                ThemaExceptionList.NOT_FOUND_THEMA_NAME.getHttpStatus(),
                ThemaExceptionList.NOT_FOUND_THEMA_NAME.getMESSAGE()
        );
    }
}
