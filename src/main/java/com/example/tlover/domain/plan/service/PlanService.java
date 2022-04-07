package com.example.tlover.domain.plan.service;

import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.dto.CreatePlanResponse;
import com.example.tlover.domain.plan.dto.PlanDetailResponse;
import com.example.tlover.domain.plan.dto.PlanListResponse;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan_region.dto.CreatePlanRegionRequest;

import javax.validation.Valid;
import java.util.List;

public interface PlanService {
    Plan createPlan(@Valid CreatePlanRequest createPlanRequest, String loginId);

    List<PlanListResponse> getAllPlans(@Valid String loginId);

    List<PlanListResponse> getPlansByState(@Valid String loginId, String status);

    PlanDetailResponse getPlanDetail(String planTitle, String loginId);
}
