package com.example.tlover.domain.diary.exception;

public class NotFoundAcceptDiaryException extends DiaryException {
    public NotFoundAcceptDiaryException() {
        super(DiaryExceptionList.NOT_FOUND_ACCEPT_DIARY.getCODE(),
                DiaryExceptionList.NOT_FOUND_ACCEPT_DIARY.getHttpStatus(),
                DiaryExceptionList.NOT_FOUND_ACCEPT_DIARY.getMESSAGE()
        );
    }
}
