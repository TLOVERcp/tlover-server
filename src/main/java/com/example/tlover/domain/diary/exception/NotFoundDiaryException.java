package com.example.tlover.domain.diary.exception;

import com.example.tlover.domain.myfile.constant.MyFileConstants;

public class NotFoundDiaryException extends IllegalArgumentException {
    public NotFoundDiaryException() {
        super("해당 diaryId로 Diary를 찾을 수 없습니다.");
    }
}
