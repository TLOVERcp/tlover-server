package com.example.tlover.domain.plan.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum planExceptionList {
    NO_AUTHORITY_PLAN("P0001", HttpStatus.FORBIDDEN, "해당 계획에 접근 권한이 없는 유저입니다."),
    NOT_FOUND_PLAN("P0002", HttpStatus.NOT_FOUND, "해당 계획을 찾을 수 없습니다."),
    ANOTHER_USER_EDITING("P0003", HttpStatus.FORBIDDEN, "다른 유저가 수정중인 계획입니다."),
    NO_AUTHORITY_DELETE("P0004", HttpStatus.FORBIDDEN, "해당 계획에 삭제 권한이 없는 유저입니다."),
    ALREADY_DELETE_PLAN("P0005", HttpStatus.BAD_REQUEST, "이미 삭제된 계획입니다.");

    private final String CODE;
    private final org.springframework.http.HttpStatus httpStatus;
    private final String MESSAGE;

}
