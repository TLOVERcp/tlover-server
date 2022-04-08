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
@ApiModel(description = "계획 삭제를 위한 응답 객체")
public class DeletePlanResponse {
    private String message;
}
