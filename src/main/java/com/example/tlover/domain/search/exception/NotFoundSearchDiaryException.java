package com.example.tlover.domain.search.exception;

public class NotFoundSearchDiaryException extends SearchException {
    public NotFoundSearchDiaryException() {
        super(
                SearchExceptionList.NOT_FOUND_SEARCH_DIARY.getCODE(),
                SearchExceptionList.NOT_FOUND_SEARCH_DIARY.getHttpStatus(),
                SearchExceptionList.NOT_FOUND_SEARCH_DIARY.getMESSAGE()
                );
    }
}
