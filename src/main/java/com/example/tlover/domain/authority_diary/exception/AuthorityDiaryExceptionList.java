package com.example.tlover.domain.authority_diary.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthorityDiaryExceptionList {

    SUCCESS("AD0000", HttpStatus.OK, "요청에 성공하였습니다."),
    ALREADY_HOST("AD0001", HttpStatus.INTERNAL_SERVER_ERROR,"이미 호스트 입니다."),

    ALREADY_REQUEST("AD0002", HttpStatus.INTERNAL_SERVER_ERROR,"요청을 수락한 유저입니다."),
    ALREADY_ACCEPT("AD0003", HttpStatus.INTERNAL_SERVER_ERROR,"이미 권한 요청을 한 유저입니다."),
    ONLY_REQUEST("AD0004", HttpStatus.INTERNAL_SERVER_ERROR,"오직 REUEST 일때만 수락이 가능합니다."),
    NOT_FOUND_AUTHORITY("AD0005", HttpStatus.NOT_FOUND,"권한을 찾을수 없습니다.");

    public final String CODE;
    public final HttpStatus httpStatus;
    public final String MESSAGE;


}
