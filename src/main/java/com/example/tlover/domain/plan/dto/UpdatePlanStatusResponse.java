package com.example.tlover.domain.plan.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "수정 상태 변경을 위한 응답 객체")
public class UpdatePlanStatusResponse {
    private String message;
}
