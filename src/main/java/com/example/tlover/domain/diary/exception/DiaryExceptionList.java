package com.example.tlover.domain.diary.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DiaryExceptionList {
    SUCCESS("D0000",HttpStatus.OK, "요청에 성공하였습니다."),
    NO_SUCH_ELEMENT("D0001", HttpStatus.NOT_FOUND,"인자를 찾을 수 없습니다."),
    ALREADY_EXIST_DIARY("D0002" , HttpStatus.INTERNAL_SERVER_ERROR, "하나의 유저는 하나의 계획에 한번만 작성 가능합니다."),
    NO_AUTHORITY_DELETE("D0003" , HttpStatus.FORBIDDEN , "다이어리의 삭제 권한이 없습니다."),
    NOT_FOUND_DIARY("D0004" , HttpStatus.NOT_FOUND , "해당 diaryId로 Diary를 찾을 수 없습니다.");

    private final String CODE;
    private final HttpStatus httpStatus;
    private final String MESSAGE;

}
