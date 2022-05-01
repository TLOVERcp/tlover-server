package com.example.tlover.domain.plan.service;

import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.dto.PlanDetailResponse;
import com.example.tlover.domain.plan.dto.PlanListResponse;
import com.example.tlover.domain.plan.entity.Plan;

import javax.validation.Valid;
import java.util.List;

public interface PlanService {
    Plan createPlan(@Valid CreatePlanRequest createPlanRequest, String loginId);

    List<PlanListResponse> getAllPlans(@Valid String loginId);

    List<PlanListResponse> getPlansByState(@Valid String loginId, String status);

    PlanDetailResponse getPlanDetail(@Valid Long planId);

    Plan deletePlan(@Valid Long planId, String loginId);

    Plan updatePlan(@Valid CreatePlanRequest createPlanRequest, Long planId);

    Boolean checkUser(@Valid Long planId, String loginId );

    void updatePlanStatusFinish(@Valid Long planId);

    void updatePlanStatusEditing(@Valid Long planId);

    void updatePlanStatusActive(@Valid Long planId);

    String checkPlanStatus(@Valid Long planId);
}
