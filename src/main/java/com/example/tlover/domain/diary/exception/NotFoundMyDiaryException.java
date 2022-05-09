package com.example.tlover.domain.diary.exception;

public class NotFoundMyDiaryException extends DiaryException {
    public NotFoundMyDiaryException() {
        super(DiaryExceptionList.NOT_FOUND_MY_DIARY.getCODE(),
                DiaryExceptionList.NOT_FOUND_MY_DIARY.getHttpStatus(),
                DiaryExceptionList.NOT_FOUND_MY_DIARY.getMESSAGE()
        );
    }
}
