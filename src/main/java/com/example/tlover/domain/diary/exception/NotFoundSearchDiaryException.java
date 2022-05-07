package com.example.tlover.domain.diary.exception;

public class NotFoundSearchDiaryException extends DiaryException {
    public NotFoundSearchDiaryException() {
        super(
                DiaryExceptionList.NOT_FOUND_SEARCH_DIARY.getCODE(),
                DiaryExceptionList.NOT_FOUND_SEARCH_DIARY.getHttpStatus(),
                DiaryExceptionList.NOT_FOUND_SEARCH_DIARY.getMESSAGE()
                );
    }
}
