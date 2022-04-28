package com.example.tlover.domain.plan.dto;

import com.example.tlover.domain.authority_plan.entity.AuthorityPlan;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.example.tlover.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "여행 계획 상세 조회를 위한 응답 객체")
public class PlanDetailResponse {
    private Long planId;
    private String planTitle;
    private String planContext;
    private String planStatus;
    private LocalDateTime planStartDate;
    private LocalDateTime planEndDate;
    private LocalDateTime planWriteDate;
    //private String userNickName;
    private String[] regionName;
    private String[] users;

    public static PlanDetailResponse from(Plan plan, List<PlanRegion> planRegions, List<AuthorityPlan> authorityPlans){
        String[] regionName = new String[planRegions.size()];
        for(int i=0; i< regionName.length; i++){
            regionName[i] = planRegions.get(i).getRegion().getRegionName();
        }
        String[] users = new String[authorityPlans.size()];
        for(int i=0; i< authorityPlans.size(); i++){
            users[i] = authorityPlans.get(i).getUser().getUserNickName();
        }
        return PlanDetailResponse.builder()
                .planId(plan.getPlanId())
                .planTitle(plan.getPlanTitle())
                .planContext(plan.getPlanContext())
                .planStatus(plan.getPlanStatus())
                .planStartDate(plan.getPlanStartDate())
                .planEndDate(plan.getPlanEndDate())
                .planWriteDate(plan.getPlanWriteDate())
                //.userNickName(plan.getUser().getUserNickName())
                .regionName(regionName)
                .users(users)
                .build();
    }
}
