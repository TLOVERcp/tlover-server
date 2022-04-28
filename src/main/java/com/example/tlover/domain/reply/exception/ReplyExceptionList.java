package com.example.tlover.domain.reply.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReplyExceptionList {

    NOT_EQUAL_USER_ID("RP0001",HttpStatus.BAD_REQUEST, "댓글 작성자가 일치하지 않습니다."),
    NOT_FIND_REPLY("RP0002", HttpStatus.FORBIDDEN, "댓글 id와 맞는 댓글을 찾을 수 없습니다."),
    WRONG_STATE("RP0003", HttpStatus.FORBIDDEN, "상태값이 올바르지 않습니다.");

    private final String CODE;
    private final org.springframework.http.HttpStatus httpStatus;
    private final String MESSAGE;

}
