package com.example.tlover.global.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ApiErrorResponse {

    private LocalDateTime timeStamp;
    private String errorCode;
    private List<String> message;

    public ApiErrorResponse(String errorCode,List<String> message) {
        this.timeStamp = LocalDateTime.now().withNano(0);
        this.errorCode = errorCode;
        this.message = message;
    }
}
