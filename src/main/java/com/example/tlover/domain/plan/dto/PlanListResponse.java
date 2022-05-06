package com.example.tlover.domain.plan.dto;

import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.example.tlover.domain.user.dto.ProfileResponse;
import com.example.tlover.domain.user.entity.User;
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
@ApiModel(description = "여행 계획 목록 조회를 위한 응답 객체")
public class PlanListResponse {
    private Long planId;
    private String planTitle;
    private String planStatus;
    private LocalDateTime planStartDate;
    private LocalDateTime planEndDate;
    private Long day;
    private String[] regionName;

   public static PlanListResponse from(Plan plan, List<PlanRegion> planRegions) {
       String[] regionName = new String[planRegions.size()];
       for(int i=0; i< regionName.length; i++){
           regionName[i] = planRegions.get(i).getRegion().getRegionName();
       }
       long period = ChronoUnit.DAYS.between(plan.getPlanStartDate().toLocalDate(), plan.getPlanEndDate().toLocalDate())+1;
       long day = ChronoUnit.DAYS.between(plan.getPlanStartDate().toLocalDate(), LocalDateTime.now().toLocalDate())+1;
       if(period-day<0||day<0){ //지났으면 양수 , 끝나는 조건 추가하기
           day = -1;
       }

        return PlanListResponse.builder()
                .planId(plan.getPlanId())
                .planTitle(plan.getPlanTitle())
                .planStatus(plan.getPlanStatus())
                .planStartDate(plan.getPlanStartDate())
                .planEndDate(plan.getPlanEndDate())
                .day(day)
                .regionName(regionName)
                .build();
    }
}
