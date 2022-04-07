package com.example.tlover.domain.plan.dto;

import com.example.tlover.domain.plan.entity.Plan;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "계획 작성을 위한 응답 객체")
public class CreatePlanResponse {

    private String message;

    public static CreatePlanResponse from(Plan plan) {
        return CreatePlanResponse.builder()
                .message("계획 작성에 성공했습니다.")
                .build();
    }
}
