package com.example.tlover.domain.reply.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReplyExceptionMessage {

    NOT_EQUAL_USER_ID_EXCEPTION_MESSAGE("댓글 작성자가 일치하지 않습니다."),
    NOT_FIND_REPLY_EXCEPTION_MESSAGE("댓글 id와 맞는 댓글을 찾을 수 없습니다.");



    private final String message;

}
