package com.example.tlover.domain.plan.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PlanExceptionList {
    NO_AUTHORITY_PLAN("P0001", HttpStatus.FORBIDDEN, "해당 계획에 접근 권한이 없는 유저입니다."),
    NOT_FOUND_PLAN("P0002", HttpStatus.NOT_FOUND, "해당 계획을 찾을 수 없습니다."),
    ANOTHER_USER_EDITING("P0003", HttpStatus.FORBIDDEN, "다른 유저가 수정중인 계획입니다."),
    NO_AUTHORITY_DELETE("P0004", HttpStatus.FORBIDDEN, "해당 계획에 삭제 권한이 없는 유저입니다."),
    ALREADY_DELETE_PLAN("P0005", HttpStatus.BAD_REQUEST, "이미 삭제된 계획입니다."),
    ALREADY_FINISH_PLAN("P0006", HttpStatus.BAD_REQUEST, "이미 작성이 완료되어 수정/삭제가 불가능합니다."),
    NO_EDITING_STATUS("P0007",HttpStatus.FORBIDDEN,"수정이 불가능한 상태입니다."),


    DENIED_SHARE_HOST("P0010", HttpStatus.BAD_REQUEST, "공유가 불가능한 유저(HOST)입니다."),
    DENIED_SHARE_REQUEST("P0011", HttpStatus.BAD_REQUEST, "해당 유저에게 이미 계획 공유 요청중입니다."),
    DENIED_SHARE_ACCEPT("P0012", HttpStatus.BAD_REQUEST, "이미 공유된 계획입니다."),
    NO_AUTHORITY_SHARE("P0013", HttpStatus.FORBIDDEN, "해당 계획에 공유 권한이 없습니다."),
    NOT_FOUND_AUTHORITYPLAN("P0014", HttpStatus.NOT_FOUND, "계획 권한을 찾을 수 없습니다.")
    ;

    private final String CODE;
    private final org.springframework.http.HttpStatus httpStatus;
    private final String MESSAGE;

}
