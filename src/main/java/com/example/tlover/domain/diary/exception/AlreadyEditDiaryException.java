package com.example.tlover.domain.diary.exception;

import static com.example.tlover.domain.diary.exception.DiaryExceptionList.*;

public class AlreadyEditDiaryException extends DiaryException {
    public AlreadyEditDiaryException() {
        super(
                ALREADY_EDIT_DIARY_EXCEPTION.getCODE(),
                ALREADY_EDIT_DIARY_EXCEPTION.getHttpStatus(),
                ALREADY_EDIT_DIARY_EXCEPTION.getMESSAGE()
        );
    }
}
