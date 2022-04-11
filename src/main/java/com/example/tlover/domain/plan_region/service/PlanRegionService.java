package com.example.tlover.domain.plan_region.service;


import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.entity.Plan;

import javax.validation.Valid;

public interface PlanRegionService {
    void createPlanRegion(@Valid CreatePlanRequest createPlanRequest, Plan plan);

    void updatePlanRegion(@Valid CreatePlanRequest createPlanRequest, Plan plan);

    void deletePlanRegion(@Valid Plan plan);
}
