package com.example.tlover.domain.authority.dto;

import com.example.tlover.domain.authority.entity.AuthorityPlan;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "여행 계획 권한 공유 요청 목록 조회를 위한 응답 객체")
public class AuthorityPlanListResponse {
    private Long authorityPlanId;
    private String planTitle;
    private String planUserNickName;
    private String planStatus;
    private String authorityPlanStatus;
    private String authorityPlanShareDate;

    public static AuthorityPlanListResponse from(AuthorityPlan authorityPlans) {
        return AuthorityPlanListResponse.builder()
                .authorityPlanId(authorityPlans.getAuthorityPlanId())
                .authorityPlanShareDate(authorityPlans.getAuthorityPlanShareDate())
                .authorityPlanStatus(authorityPlans.getAuthorityPlanStatus())
                .planStatus(authorityPlans.getPlan().getPlanStatus())
                .planTitle(authorityPlans.getPlan().getPlanTitle())
                .planUserNickName(authorityPlans.getPlan().getUser().getUserNickName())
                .build();

    }
}
