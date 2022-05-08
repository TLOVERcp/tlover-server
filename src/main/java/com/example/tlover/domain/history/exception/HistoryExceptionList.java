package com.example.tlover.domain.history.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HistoryExceptionList {
    REJECT_GET_DIARY("H0001", HttpStatus.BAD_REQUEST, "해당 다이어리는 조회할 수 없는 상태입니다."),
    REJECT_DELETED_DIARY("H0002", HttpStatus.BAD_REQUEST, "해당 다이어리는 삭제된 상태입니다.");

    private final String CODE;
    private final org.springframework.http.HttpStatus httpStatus;
    private final String MESSAGE;
}
