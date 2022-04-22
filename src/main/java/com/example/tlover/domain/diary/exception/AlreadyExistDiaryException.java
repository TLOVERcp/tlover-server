package com.example.tlover.domain.diary.exception;

public class AlreadyExistDiaryException extends RuntimeException{
    public AlreadyExistDiaryException() {
        super("하나의 여행계획에 유저당 하나씩 작성이 가능합니다.");
    }
}
