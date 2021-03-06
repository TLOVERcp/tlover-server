package com.example.tlover.domain.history.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "방문 기록 삭제를 위한 응답 객체")
public class DeleteHistoryResponse {
    private String message;
}
