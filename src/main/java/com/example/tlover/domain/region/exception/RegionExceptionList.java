package com.example.tlover.domain.region.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RegionExceptionList {

    NOT_FOUND_REGION_NAME("R0001",HttpStatus.BAD_REQUEST, "해당 지역 이름을 찾지 못했습니다.");

    private final String CODE;
    private final HttpStatus httpStatus;
    private final String MESSAGE;

}
