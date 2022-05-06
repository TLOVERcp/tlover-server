package com.example.tlover.domain.plan.dto;

import com.example.tlover.domain.authority_plan.entity.AuthorityPlan;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private Long day;
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
        long period = ChronoUnit.DAYS.between(plan.getPlanStartDate().toLocalDate(), plan.getPlanEndDate().toLocalDate())+1;
        long day = ChronoUnit.DAYS.between(plan.getPlanStartDate().toLocalDate(), LocalDateTime.now().toLocalDate())+1;
    // 2 3 4 5 6 7 6 5
        if(period-day<0||day<0){ //지났으면 양수 , 끝나는 조건 추가하기
            day = -1;
        }

        return PlanDetailResponse.builder()
                .planId(plan.getPlanId())
                .planTitle(plan.getPlanTitle())
                .planContext(plan.getPlanContext())
                .planStatus(plan.getPlanStatus())
                .planStartDate(plan.getPlanStartDate())
                .planEndDate(plan.getPlanEndDate())
                .planWriteDate(plan.getPlanWriteDate())
                .day(day)
                .regionName(regionName)
                .users(users)
                .build();
    }
}
