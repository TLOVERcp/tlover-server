package com.example.tlover.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private String message;
    private T data;
//    private List<String> result;

    public static <T> ResponseDto<T> create(String message) {
        return new ResponseDto<>(message, null);
    }
    public static <T> ResponseDto<T> create(String message, T dto) {
        return new ResponseDto<>(message, dto);
    }
}
