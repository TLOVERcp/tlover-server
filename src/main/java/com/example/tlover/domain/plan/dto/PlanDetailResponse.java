package com.example.tlover.domain.plan.dto;

import com.example.tlover.domain.authority_plan.entity.AuthorityPlan;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private String planStartDate;
    private String planEndDate;
    private String planWriteDate;
    private Long day;
    private Long expense;
    private String[] regionName;
    private String[] users;

    @QueryProjection
    public PlanDetailResponse(Long planId, String planTitle, String planContext, String planStatus, String planStartDate
        , String planEndDate, String planWriteDate, Long day, String[] regionName, String[] users, Long expense){
        this.planId = planId;
        this.planTitle = planTitle;
        this.planContext = planContext;
        this.planStatus = planStatus;
        this.planStartDate = planStartDate;
        this.planEndDate = planEndDate;
        this.planWriteDate = planWriteDate;
        this.day = day;
        this.regionName = regionName;
        this.users = users;
        this.expense = expense;
    }

    public static PlanDetailResponse from(Plan plan, List<PlanRegion> planRegions, List<AuthorityPlan> authorityPlans){
        String[] regionName = new String[planRegions.size()];
        for(int i=0; i< regionName.length; i++){
            regionName[i] = planRegions.get(i).getRegion().getRegionName();
        }
        String[] users = new String[authorityPlans.size()];
        for(int i=0; i< authorityPlans.size(); i++){
            users[i] = authorityPlans.get(i).getUser().getUserNickName();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        long period = ChronoUnit.DAYS.between(LocalDateTime.parse(plan.getPlanStartDate(),formatter).toLocalDate(), LocalDateTime.parse(plan.getPlanEndDate(),formatter).toLocalDate())+1;
        long day = ChronoUnit.DAYS.between(LocalDateTime.parse(plan.getPlanStartDate(),formatter).toLocalDate(), LocalDate.now())+1;
        if(period-day<0||day<0){
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
                .expense(plan.getExpense())
                .day(day)
                .regionName(regionName)
                .users(users)
                .build();
    }
}
