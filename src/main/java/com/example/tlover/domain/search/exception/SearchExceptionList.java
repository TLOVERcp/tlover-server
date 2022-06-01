package com.example.tlover.domain.search.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum SearchExceptionList {

    NOT_FOUND_SEARCH_DIARY("S0001" , HttpStatus.NOT_FOUND , "검색된 다이어리가 없습니다."),
    NOT_FOUND_SEARCH_USER("S0002" , HttpStatus.NOT_FOUND , "검색된 사용자가 없습니다.");


    public final String CODE;
    public final HttpStatus httpStatus;
    public final String MESSAGE;

}
