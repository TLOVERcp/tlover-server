package com.example.tlover.domain.user_thema.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserThemaExceptionMessage {

    NOT_FOUND_USER_THEMA_EXCEPTION_MESSAGE("해당 유저 테마를 찾지 못했습니다.");


    private final String message;

}
