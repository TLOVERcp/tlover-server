package com.example.tlover.domain.plan.dto;

import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.user.dto.ProfileResponse;
import com.example.tlover.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "여행 계획 목록 조회를 위한 응답 객체")
public class PlanListResponse {
    private Long planId;
    private String planTitle;
    // private String planContext;
    private String planStatus;

   public static PlanListResponse from(Plan plan) {
        return PlanListResponse.builder()
                .planId(plan.getPlanId())
                .planTitle(plan.getPlanTitle())
                .planStatus(plan.getPlanStatus())
                .build();
    }
}
