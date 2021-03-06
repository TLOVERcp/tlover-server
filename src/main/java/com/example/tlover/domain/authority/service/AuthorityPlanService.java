package com.example.tlover.domain.authority.service;

import com.example.tlover.domain.authority.dto.AuthorityPlanListResponse;
import com.example.tlover.domain.authority.dto.SharePlanRequest;
import com.example.tlover.domain.plan.entity.Plan;

import javax.validation.Valid;
import java.util.List;

public interface AuthorityPlanService {

    void sharePlan(@Valid Long planId, SharePlanRequest sharePlanRequest);

    void addPlanUser(@Valid Plan plan, String loginId);

    void deleteAuthorityPlan(@Valid Plan plan);

    List<AuthorityPlanListResponse> getSharePlanList(@Valid String loginId);

    void updateAcceptAuthorityPlan(@Valid Long authorityPlanId);

    void updateRejectAuthorityPlan(@Valid Long authorityPlanId);

    boolean checkAuthority(String loginId, Long planId);
}
