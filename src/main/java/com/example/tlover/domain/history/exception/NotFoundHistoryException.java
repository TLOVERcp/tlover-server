package com.example.tlover.domain.history.exception;

public class NotFoundHistoryException extends HistoryException {
    public NotFoundHistoryException() {
        super(HistoryExceptionList.NOT_FOUND_HISTORY.getCODE(),
                HistoryExceptionList.NOT_FOUND_HISTORY.getHttpStatus(),
                HistoryExceptionList.NOT_FOUND_HISTORY.getMESSAGE());
    }
}
