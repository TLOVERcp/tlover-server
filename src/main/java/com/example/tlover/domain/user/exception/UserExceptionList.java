package com.example.tlover.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionList {

    INVALID_PASSWORD("U0001",HttpStatus.BAD_REQUEST, "비밀번호가 잘못되었습니다."),
    NOT_FOUND_USER("U0002", HttpStatus.FORBIDDEN, "해당 아이디를 찾을 수 없습니다."),
    USER_ID_DUPLICATE("U0003", HttpStatus.CONFLICT, "해당 아이디는 이미 존재하는 아이디입니다."),
    DENIED_ACCESS("U0004", HttpStatus.FORBIDDEN, "잘못된 접근입니다."),
    NOT_EQUAL_PASSWORD("U0005", HttpStatus.BAD_REQUEST, "입력하신 비밀번호가 기존의 비밀번호와 일치하지 않습니다."),
    PASSWORD_EQUAL("U0006", HttpStatus.BAD_REQUEST, "변경할 비밀번호가 기존의 비밀번호와 동일합니다."),
    NOT_CERTIFIED("U0007", HttpStatus.FORBIDDEN, "인증 코드가 일치하지 않습니다."),
    PHONE_NUM_DUPLICATE("U0008", HttpStatus.CONFLICT, "해당 전화번호는 이미 존재하는 전화번호입니다."),
    USER_NICKNAME_DUPLICATE("U0009", HttpStatus.CONFLICT, "해당 닉네임은 이미 존재하는 닉네임입니다.");

    private final String CODE;
    private final org.springframework.http.HttpStatus httpStatus;
    private final String MESSAGE;

}
