package com.example.tlover.domain.authority_plan.service;

import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;
import com.example.tlover.domain.authority_plan.entity.AuthorityPlan;
import com.example.tlover.domain.authority_plan.repository.AuthorityPlanRepository;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.repository.PlanRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityPlanServiceImpl implements AuthorityPlanService{
    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final AuthorityPlanRepository authorityPlanRepository;

    @Override
    public void sharePlan(Long planId, SharePlanRequest sharePlanRequest) {
        Plan plan = planRepository.findByPlanId(planId);
        User user = userRepository.findByUserNickName(sharePlanRequest.getUserNickName());
        AuthorityPlan authorityPlan = AuthorityPlan.toEntity(plan, user);
        authorityPlanRepository.save(authorityPlan);
    }
}
