package com.example.tlover.domain.history.exception;

public class RejectGetDiaryException extends HistoryException {
    public RejectGetDiaryException() {
        super(HistoryExceptionList.REJECT_GET_DIARY.getCODE(),
                HistoryExceptionList.REJECT_GET_DIARY.getHttpStatus(),
                HistoryExceptionList.REJECT_GET_DIARY.getMESSAGE());
    }
}
