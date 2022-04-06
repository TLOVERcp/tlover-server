package com.example.tlover.domain.plan.service;

import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.dto.PlanDetailResponse;
import com.example.tlover.domain.plan.dto.PlanListResponse;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.repository.PlanRepository;
import com.example.tlover.domain.plan_region.dto.CreatePlanRegionRequest;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.example.tlover.domain.plan_region.repository.PlanRegionRepository;
import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService{

    private final PlanRepository planRepository;
    private final PlanRegionRepository planRegionRepository;
    private final UserRepository userRepository;

    @Override
    public Plan createPlan(CreatePlanRequest createPlanRequest, String loginId){
        User user = userRepository.findByUserLoginId(loginId).get();
        Plan plan = Plan.toEntity(createPlanRequest, user);
        planRepository.save(plan);
        return plan;
    }

    @Override
    public List<PlanListResponse> getAllPlans(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        List<Plan> plans = planRepository.findAllByUser(user);
        List<PlanListResponse> planList = new ArrayList<>();
        for(Plan p : plans){
            planList.add(PlanListResponse.from(p));
        }
        return planList;
    }

    @Override
    public List<PlanListResponse> getPlansByState(String loginId, String status) {
        User user = userRepository.findByUserLoginId(loginId).get();
        List<Plan> plans = planRepository.findAllByUserAndPlanStatus(user, status);
        List<PlanListResponse> planList = new ArrayList<>();
        for(Plan p : plans){
            planList.add(PlanListResponse.from(p));
        }
        return planList;

    }

    @Override
    public PlanDetailResponse getPlanDetail(String planTitle, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        Plan plan = planRepository.findByUserAndPlanTitle(user, planTitle);
        List<PlanRegion> planRegion = planRegionRepository.findAllByPlan(plan);
        return PlanDetailResponse.from(plan, planRegion);
    }
}
