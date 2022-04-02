package com.example.tlover.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionMessage {

    INVALID_PASSWORD_EXCEPTION_MESSAGE("Password가 잘못되었습니다."),
    NOT_FOUND_USER_EXCEPTION_MESSAGE("해당 LoginId의 User를 찾을 수 없습니다."),
    USER_ID_DUPLICATE_EXCEPTION_MASSAGE("해당 아이디는 이미 존재하는 아이디입니다.");


    private final String message;

}
