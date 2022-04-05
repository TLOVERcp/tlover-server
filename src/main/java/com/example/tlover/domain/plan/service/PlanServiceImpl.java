package com.example.tlover.domain.plan.service;

import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.repository.PlanRepository;
import com.example.tlover.domain.plan_region.dto.CreatePlanRegionRequest;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService{

    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Override
    public Plan createPlan(CreatePlanRequest createPlanRequest, String loginId){
        User user = userRepository.findByUserLoginId(loginId).get();
        Plan plan = Plan.toEntity(createPlanRequest, user);
        planRepository.save(plan);
        return plan;
    }
}
