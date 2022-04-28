package com.example.tlover.domain.diary.exception;

public class NotFoundDiaryException extends DiaryException {
    public NotFoundDiaryException() {
        super(
                DiaryExceptionList.NOT_FOUND_DIARY.getCODE(),
                DiaryExceptionList.NOT_FOUND_DIARY.getHttpStatus(),
                DiaryExceptionList.NOT_FOUND_DIARY.getMESSAGE()
                );
    }
}
