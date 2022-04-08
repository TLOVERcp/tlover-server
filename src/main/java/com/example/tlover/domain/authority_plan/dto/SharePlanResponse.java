package com.example.tlover.domain.authority_plan.dto;

import com.example.tlover.domain.plan.dto.CreatePlanResponse;
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
@ApiModel(description = "계획 권한 공유을 위한 응답 객체")
public class SharePlanResponse {
    private String message;
}
