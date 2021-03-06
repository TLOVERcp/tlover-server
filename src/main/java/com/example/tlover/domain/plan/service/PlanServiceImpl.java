package com.example.tlover.domain.plan.service;

import com.example.tlover.domain.authority.constant.AuthorityPlanConstant;
import com.example.tlover.domain.authority.entity.AuthorityPlan;
import com.example.tlover.domain.authority.repository.AuthorityPlanRepository;
import com.example.tlover.domain.plan.constant.PlanConstants;
import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.dto.PlanDetailResponse;
import com.example.tlover.domain.plan.dto.PlanListResponse;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.exception.AlreadyDeletePlanException;
import com.example.tlover.domain.plan.exception.NoAuthorityDeleteException;
import com.example.tlover.domain.plan.exception.NotFoundAuthorityPlanException;
import com.example.tlover.domain.plan.exception.NotFoundPlanException;
import com.example.tlover.domain.plan.repository.PlanRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService{
    private final PlanRepository planRepository;
    private final AuthorityPlanRepository authorityPlanRepository;
    private final UserRepository userRepository;

    @Override
    public Plan createPlan(CreatePlanRequest createPlanRequest, String loginId){
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        String regionDetail = toString(createPlanRequest.getRegionName());
        Plan plan = Plan.toEntity(regionDetail, createPlanRequest, user);
        planRepository.save(plan);
        return plan;
    }

    @Override
    public List<PlanListResponse> getAllPlans(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        List<AuthorityPlan> authorityPlans = findAuthorityPlan(user);
        List<PlanListResponse> planList = new ArrayList<>();
        for(int i=0; i<authorityPlans.size(); i++) {
            Plan p = authorityPlans.get(i).getPlan();
            planList.add(PlanListResponse.from(p));
        }
        return planList;
    }

    @Override
    public List<PlanListResponse> getPlansByState(String loginId, String status) { // active, finish
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        List<AuthorityPlan> authorityPlans = findAuthorityPlan(user);
        authorityPlans = checkStatus(authorityPlans, status);
        List<PlanListResponse> planList = new ArrayList<>();
        for(int i=0; i<authorityPlans.size(); i++) {
            Plan p = authorityPlans.get(i).getPlan();
            planList.add(PlanListResponse.from(p));
        }
        return planList;
    }

    @Override
    public PlanDetailResponse getPlanDetail(Long planId) {
        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        Optional<List<AuthorityPlan>> hostPlans = authorityPlanRepository.findAllByPlanAndAuthorityPlanStatus(plan,"HOST");
        Optional<List<AuthorityPlan>> authorityPlans = authorityPlanRepository.findAllByPlanAndAuthorityPlanStatus(plan,"ACCEPT");
        List<AuthorityPlan> plans = new ArrayList<>();
        if(hostPlans.isPresent()){
            plans.addAll(hostPlans.get());
        }
        if(authorityPlans.isPresent()){
            plans.addAll(authorityPlans.get());
        }
        plans = checkDelete(plans);
        return PlanDetailResponse.from(plan, plans);
    }

    @Override
    @Transactional
    public Plan deletePlan(Long planId, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        if(plan.getPlanStatus().equals(String.valueOf(PlanConstants.EPlanStatus.DELETE)))
            throw new AlreadyDeletePlanException();
        if(!user.getUserId().equals(plan.getUser().getUserId()))
            throw new NoAuthorityDeleteException();
        plan.setPlanStatus(String.valueOf(PlanConstants.EPlanStatus.DELETE));
        return plan;
    }

    @Override
    public Plan updatePlan(CreatePlanRequest createPlanRequest, Long planId) {
        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        String regionDetail = toString(createPlanRequest.getRegionName());
        plan.updatePlan(regionDetail, createPlanRequest, plan);
        return plan;
    }

    @Override
    @Transactional
    public void updatePlanStatusFinish(Long planId) {
        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        plan.setPlanStatus(String.valueOf(PlanConstants.EPlanStatus.FINISH));
    }

    @Override
    @Transactional
    public void updatePlanStatusEditing(Long planId) {
        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        plan.setPlanStatus(String.valueOf(PlanConstants.EPlanStatus.EDITING));
    }

    @Override
    @Transactional
    public void updatePlanStatusActive(Long planId) {
        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        plan.setPlanStatus(String.valueOf(PlanConstants.EPlanStatus.ACTIVE));
    }

    @Override
    public String checkPlanStatus(Long planId) {
        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        return plan.getPlanStatus();
    }

    @Override
    public Boolean checkUser(Long planId, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        AuthorityPlan authorityPlans = authorityPlanRepository.findAllByPlanAndUser(plan, user).orElseThrow(NotFoundAuthorityPlanException::new);
        if(authorityPlans.getAuthorityPlanStatus().equals(String.valueOf(AuthorityPlanConstant.EAuthorityPlanState.HOST))||authorityPlans.getAuthorityPlanStatus().equals("ACCEPT"))
                return true;
        return false;
    }

    public List<AuthorityPlan> findAuthorityPlan(User user){
        Optional<List<AuthorityPlan>> hostPlans = authorityPlanRepository.findAllByUserAndAuthorityPlanStatus(user,"HOST");
        Optional<List<AuthorityPlan>> authorityPlans = authorityPlanRepository.findAllByUserAndAuthorityPlanStatus(user,"ACCEPT");
        List<AuthorityPlan> plans = new ArrayList<>();
        if(hostPlans.isPresent()){
            plans.addAll(hostPlans.get());
        }
        if(authorityPlans.isPresent()){
            plans.addAll(authorityPlans.get());
        }
        plans = checkDelete(plans);
        return plans;
    }

    public static List<AuthorityPlan> checkDelete(List<AuthorityPlan> authorityPlans) {
        for(int i=0; i<authorityPlans.size(); i++) {
            if (authorityPlans.get(i).getPlan().getPlanStatus().equals(String.valueOf(PlanConstants.EPlanStatus.DELETE))) {
                authorityPlans.remove(i);
            }
        }
        return authorityPlans;
    }

    private List<AuthorityPlan> checkStatus(List<AuthorityPlan> authorityPlans, String status) {
        for(int i=0; i<authorityPlans.size(); i++) {
            if (!authorityPlans.get(i).getPlan().getPlanStatus().equals(status)) {
                authorityPlans.remove(i);
            }
        }
        return authorityPlans;
    }

    private String toString(String[] regionName){
        String regionDetail = String.join(", ", regionName);
        return regionDetail;
    }


}
