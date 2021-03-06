package com.example.tlover.domain.authority.service;

import com.example.tlover.domain.authority.constant.AuthorityPlanConstant;
import com.example.tlover.domain.authority.dto.AuthorityPlanListResponse;
import com.example.tlover.domain.authority.dto.SharePlanRequest;
import com.example.tlover.domain.authority.entity.AuthorityPlan;
import com.example.tlover.domain.authority.repository.AuthorityPlanRepository;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.exception.*;
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
                if (authorityPlan.getAuthorityPlanStatus().equals(String.valueOf(AuthorityPlanConstant.EAuthorityPlanState.ACCEPT))) throw new DeniedShareAcceptException();
                if (authorityPlan.getAuthorityPlanStatus().equals(String.valueOf(AuthorityPlanConstant.EAuthorityPlanState.REQUEST))) throw new DeniedShareRequestException();
                if (authorityPlan.getAuthorityPlanStatus().equals(String.valueOf(AuthorityPlanConstant.EAuthorityPlanState.HOST))) throw new DeniedShareHostException();
            }
        }
            AuthorityPlan authorityPlan = AuthorityPlan.toEntity(plan, user,String.valueOf(AuthorityPlanConstant.EAuthorityPlanState.REQUEST));
            authorityPlanRepository.save(authorityPlan);
    }

    //원글쓴이 권한 저장
    @Override
    public void addPlanUser(Plan plan, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        AuthorityPlan authorityPlan = AuthorityPlan.toEntity(plan, user, String.valueOf(AuthorityPlanConstant.EAuthorityPlanState.HOST));
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
        if(authorityPlanRepository.findAllByUserAndAuthorityPlanStatus(user, String.valueOf(AuthorityPlanConstant.EAuthorityPlanState.REQUEST)).isPresent())
            authorityPlans = authorityPlanRepository.findAllByUserAndAuthorityPlanStatus(user, String.valueOf(AuthorityPlanConstant.EAuthorityPlanState.REQUEST)).get();
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
        AuthorityPlan authorityPlan = authorityPlanRepository.findByAuthorityPlanId(authorityPlanId).orElseThrow(NotFoundAuthorityPlanException::new);
        authorityPlan.setAuthorityPlanStatus(String.valueOf(AuthorityPlanConstant.EAuthorityPlanState.ACCEPT));
    }

    @Override
    @Transactional
    public void updateRejectAuthorityPlan(Long authorityPlanId) {
        AuthorityPlan authorityPlan = authorityPlanRepository.findByAuthorityPlanId(authorityPlanId).orElseThrow(NotFoundAuthorityPlanException::new);
        authorityPlan.setAuthorityPlanStatus(String.valueOf(AuthorityPlanConstant.EAuthorityPlanState.REJECT));
    }

    @Override
    public boolean checkAuthority(String loginId, Long planId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        Optional<List<AuthorityPlan>> sharePlan = authorityPlanRepository.findAllByUserAndPlan(user,plan);
        if(sharePlan.isPresent()){
            for(AuthorityPlan authorityPlan : sharePlan.get()){
                if(authorityPlan.getAuthorityPlanStatus().equals(String.valueOf(AuthorityPlanConstant.EAuthorityPlanState.HOST))) return true;
            }
        }

        return false;
    }


}
