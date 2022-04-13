package com.example.tlover.domain.plan.service;

import com.example.tlover.domain.authority_plan.entity.AuthorityPlan;
import com.example.tlover.domain.authority_plan.repository.AuthorityPlanRepository;
import com.example.tlover.domain.authority_plan.service.AuthorityPlanServiceImpl;
import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.dto.PlanDetailResponse;
import com.example.tlover.domain.plan.dto.PlanListResponse;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.repository.PlanRepository;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.example.tlover.domain.plan_region.repository.PlanRegionRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService{

    private final PlanRepository planRepository;
    private final PlanRegionRepository planRegionRepository;
    private final AuthorityPlanRepository authorityPlanRepository;
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
        List<AuthorityPlan> authorityPlans = authorityPlanRepository.findAllByUser(user).get();
        authorityPlans = checkDelete(authorityPlans);
        List<PlanListResponse> planList = new ArrayList<>();
        for(int i=0; i<authorityPlans.size(); i++)
            planList.add(PlanListResponse.from(authorityPlans.get(i).getPlan()));
        return planList;
    }

    public static List<AuthorityPlan> checkDelete(List<AuthorityPlan> authorityPlans) {
        for(int i=0; i<authorityPlans.size(); i++) {
            if (authorityPlans.get(i).getPlan().getPlanStatus().equals("DELETE")) {
                authorityPlans.remove(i);
            }
        }
     return authorityPlans;
    }

    @Override
    public List<PlanListResponse> getPlansByState(String loginId, String status) {
        User user = userRepository.findByUserLoginId(loginId).get();
        List<AuthorityPlan> plans = authorityPlanRepository.findAllByUser(user).get();
        plans = checkStatus(plans, status);
        List<PlanListResponse> planList = new ArrayList<>();
        for(int i=0; i<plans.size(); i++)
            planList.add(PlanListResponse.from(plans.get(i).getPlan()));
        return planList;
    }

    private List<AuthorityPlan> checkStatus(List<AuthorityPlan> authorityPlans, String status) {
        for(int i=0; i<authorityPlans.size(); i++) {
            if (!authorityPlans.get(i).getPlan().getPlanStatus().equals(status)) {
                authorityPlans.remove(i);
            }
        }
        return authorityPlans;
    }

    @Override
    public PlanDetailResponse getPlanDetail(Long planId) {
        Plan plan = planRepository.findByPlanId(planId).get();
        List<PlanRegion> planRegion = planRegionRepository.findAllByPlan(plan).get();
        List<AuthorityPlan> authorityPlans = authorityPlanRepository.findAllByPlan(plan).get();
        AuthorityPlanServiceImpl.checkStatus(authorityPlans,"ACCEPT");
        return PlanDetailResponse.from(plan, planRegion, authorityPlans);
    }

    @Override
    @Transactional
    public Plan deletePlan(Long planId) {
        Plan plan = planRepository.findByPlanId(planId).get();
        plan.setPlanStatus("DELETE");
        return plan;
    }

    @Override
    @Transactional
    public void finishPlan(Long planId) {
        Plan plan = planRepository.findByPlanId(planId).get();
        plan.setPlanStatus("FINISH");
    }

    @Override
    public Plan updatePlan(CreatePlanRequest createPlanRequest, Long planId) {
        Plan plan = planRepository.findByPlanId(planId).get();
        plan.updatePlan(createPlanRequest, plan);
        return plan;
    }
}
