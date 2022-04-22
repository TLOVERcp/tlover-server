package com.example.tlover.domain.diary.exception;

public class NotAuthorityDelete extends RuntimeException{
    public
    NotAuthorityDelete() {
        super("삭제 권한이 없습니다.");
    }
}
