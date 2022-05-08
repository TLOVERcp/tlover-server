package com.example.tlover.domain.history.exception;

public class RejectDeletedDiaryException extends HistoryException {
    public RejectDeletedDiaryException() {
        super(HistoryExceptionList.REJECT_DELETED_DIARY.getCODE(),
                HistoryExceptionList.REJECT_DELETED_DIARY.getHttpStatus(),
                HistoryExceptionList.REJECT_DELETED_DIARY.getMESSAGE());
    }
}
