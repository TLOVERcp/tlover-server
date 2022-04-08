package com.example.tlover.domain.authority_plan.service;

import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;

import javax.validation.Valid;

public interface AuthorityPlanService {
    void sharePlan(@Valid Long planId, SharePlanRequest sharePlanRequest);
}
