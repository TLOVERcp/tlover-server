package com.example.tlover.domain.authority_plan.service;

import com.example.tlover.domain.authority_plan.dto.AuthorityPlanListResponse;
import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;
import com.example.tlover.domain.authority_plan.entity.AuthorityPlan;
import com.example.tlover.domain.authority_plan.repository.AuthorityPlanRepository;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.exception.DeniedShareAcceptException;
import com.example.tlover.domain.plan.exception.DeniedShareHostException;
import com.example.tlover.domain.plan.exception.DeniedShareRequestException;
import com.example.tlover.domain.plan.exception.NotFoundPlanException;
import com.example.tlover.domain.plan.repository.PlanRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorityPlanServiceImpl implements AuthorityPlanService{

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final AuthorityPlanRepository authorityPlanRepository;

    //공유 요청
    @Override
    public void sharePlan(Long planId, SharePlanRequest sharePlanRequest) {
        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        User user = userRepository.findByUserNickName(sharePlanRequest.getUserNickName()).orElseThrow(NotFoundUserException::new);
        Optional<List<AuthorityPlan>> sharePlan = authorityPlanRepository.findAllByUserAndPlan(user,plan);
        if(sharePlan.isPresent()) {
            for (AuthorityPlan authorityPlan : sharePlan.get()) {
                System.out.println(authorityPlan.getAuthorityPlanStatus());
                if (authorityPlan.getAuthorityPlanStatus().equals("REJECT")) sharePlan.get().remove(authorityPlan);
            }
        }
        if(sharePlan.isEmpty()){
            AuthorityPlan authorityPlan = AuthorityPlan.toEntity(plan, user,"REQUEST");
            authorityPlanRepository.save(authorityPlan);
        } else {
            for (AuthorityPlan authorityPlan : sharePlan.get()) {
                if (authorityPlan.getAuthorityPlanStatus().equals("ACCEPT")) throw new DeniedShareAcceptException();
                if (authorityPlan.getAuthorityPlanStatus().equals("REQUEST")) throw new DeniedShareRequestException();
                if (authorityPlan.getAuthorityPlanStatus().equals("HOST")) throw new DeniedShareHostException();
            }
        }

    }

    //원글쓴이 권한 저장
    @Override
    public void addPlanUser(Plan plan, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        AuthorityPlan authorityPlan = AuthorityPlan.toEntity(plan, user, "HOST");
        authorityPlanRepository.save(authorityPlan);
    }

    @Override
    @Transactional
    public void deleteAuthorityPlan(Plan plan) {
        authorityPlanRepository.deleteAllByPlan(plan);
    }

    @Override
    public List<AuthorityPlanListResponse> getSharePlanList(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        List<AuthorityPlan> authorityPlans = new ArrayList<>();
        if(authorityPlanRepository.findAllByUserAndAuthorityPlanStatus(user, "REQUEST").isPresent())
            authorityPlans = authorityPlanRepository.findAllByUserAndAuthorityPlanStatus(user, "REQUEST").get();
        List<AuthorityPlanListResponse> authorityPlanList = new ArrayList<>();
        for(int i=0; i<authorityPlans.size(); i++)
            authorityPlanList.add(AuthorityPlanListResponse.from(authorityPlans.get(i)));
        return authorityPlanList;
    }

    /*public static List<AuthorityPlan> checkStatus(List<AuthorityPlan> authorityPlans, String status) {
        List<AuthorityPlan> plans = new ArrayList<>();
        plans.addAll(authorityPlans);
        for(int i=0; i<plans.size(); i++) {
            if (!plans.get(i).getAuthorityPlanStatus().equals(status)) {
                plans.remove(i);
            }
        }

        return plans;
    }*/

    @Override
    @Transactional
    public void updateAcceptAuthorityPlan(Long authorityPlanId) {
        AuthorityPlan authorityPlan = authorityPlanRepository.findByAuthorityPlanId(authorityPlanId).orElseThrow(NotFoundPlanException::new);
        authorityPlan.setAuthorityPlanStatus("ACCEPT");
    }

    @Override
    @Transactional
    public void updateRejectAuthorityPlan(Long authorityPlanId) {
        AuthorityPlan authorityPlan = authorityPlanRepository.findByAuthorityPlanId(authorityPlanId).orElseThrow(NotFoundPlanException::new);
        authorityPlan.setAuthorityPlanStatus("REJECT");
    }

    @Override
    public boolean checkAuthority(String loginId, Long planId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        Optional<List<AuthorityPlan>> sharePlan = authorityPlanRepository.findAllByUserAndPlan(user,plan);
        if(sharePlan.isPresent()){
            for(AuthorityPlan authorityPlan : sharePlan.get()){
                if(authorityPlan.getAuthorityPlanStatus().equals("HOST")) return true;
            }
        }

        return false;
    }


}
