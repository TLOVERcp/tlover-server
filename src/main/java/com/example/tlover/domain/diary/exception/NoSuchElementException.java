package com.example.tlover.domain.diary.exception;

public class NoSuchElementException extends java.util.NoSuchElementException {
    public NoSuchElementException(String s) {
        super("인자를 찾을수 없습니다.");
    }
}
