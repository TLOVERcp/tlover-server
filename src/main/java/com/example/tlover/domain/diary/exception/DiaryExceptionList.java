package com.example.tlover.domain.diary.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum DiaryExceptionList {
    SUCCESS("D0000",HttpStatus.OK, "요청에 성공하였습니다."),
    NO_SUCH_ELEMENT("D0001", HttpStatus.NOT_FOUND,"인자를 찾을 수 없습니다."),
    ALREADY_EXIST_DIARY("D0002" , HttpStatus.INTERNAL_SERVER_ERROR, "하나의 유저는 하나의 계획에 한번만 작성 가능합니다."),
    NO_AUTHORITY_DELETE("D0003" , HttpStatus.FORBIDDEN , "다이어리의 삭제 권한이 없습니다."),
    NOT_FOUND_DIARY("D0004" , HttpStatus.NOT_FOUND , "해당 diaryId로 Diary를 찾을 수 없습니다."),
    NOT_FOUND_MY_DIARY("D0006", HttpStatus.NOT_FOUND, "내가 작성한 다이어리를 찾을 수 없습니다."),
    NOT_FOUND_ACCEPT_DIARY("D0007", HttpStatus.NOT_FOUND, "내가 초대된 다이어리를 찾을 수 없습니다."),
    NOT_FOUND_GOOD_WEATHER("D008", HttpStatus.NOT_FOUND, "국내에 관광기후 매우 좋음이 없습니다."),
    NO_AUTHORITY_MODIFY("D0009" , HttpStatus.FORBIDDEN , "다이어리의 수정 권한이 없습니다."),
    NO_CORRECT_DAY("D0010" , HttpStatus.INTERNAL_SERVER_ERROR, "계획의 일자가 잘못되었습니다." ),

    ALREADY_EDIT_DIARY_EXCEPTION("D0011" , HttpStatus.INTERNAL_SERVER_ERROR, "현재 수정중입니다.");

    public final String CODE;
    public final HttpStatus httpStatus;
    public final String MESSAGE;

}
