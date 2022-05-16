package com.example.tlover.global.search.exception;

public class NotFoundSearchUserException extends SearchException {
    public NotFoundSearchUserException() {
        super(
                SearchExceptionList.NOT_FOUND_SEARCH_USER.getCODE(),
                SearchExceptionList.NOT_FOUND_SEARCH_USER.getHttpStatus(),
                SearchExceptionList.NOT_FOUND_SEARCH_USER.getMESSAGE()
                );
    }
}
