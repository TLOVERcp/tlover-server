package com.example.tlover.domain.rating.exception;

public class NotFoundRatingException extends RatingException {
        public NotFoundRatingException() {
                super(RatingExceptionList.NOT_FOUND_RATING.getCODE(),
                        RatingExceptionList.NOT_FOUND_RATING.getHttpStatus(),
                        RatingExceptionList.NOT_FOUND_RATING.getMESSAGE()
                );
        }

}
