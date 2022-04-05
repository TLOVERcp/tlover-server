package com.example.tlover.domain.plan.service;

import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.dto.CreatePlanResponse;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan_region.dto.CreatePlanRegionRequest;

import javax.validation.Valid;

public interface PlanService {
    Plan createPlan(@Valid CreatePlanRequest createPlanRequest, String loginId);
}
