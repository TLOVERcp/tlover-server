package com.example.tlover.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionMessage {

    INVALID_PASSWORD_EXCEPTION_MESSAGE("비밀번호가 잘못되었습니다."),
    NOT_FOUND_USER_EXCEPTION_MESSAGE("해당 아이디를 찾을 수 없습니다."),
    USER_ID_DUPLICATE_EXCEPTION_MASSAGE("해당 아이디는 이미 존재하는 아이디입니다."),
    DENIED_ACCESS_EXCEPTION_MASSAGE("잘못된 접근입니다."),
    NOT_EQUAL_PASSWORD_MASSAGE("기존의 비밀번호와 일치하지 않습니다."),
    PASSWORD_EQUAL_EXCEPTION_MASSAGE("변경할 비밀번호와 일치합니다.");


    private final String message;

}
