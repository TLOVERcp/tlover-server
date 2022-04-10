package com.example.tlover.domain.plan.repository;

import com.example.tlover.domain.plan.dto.PlanListResponse;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findAllByUser(User user);

    List<Plan> findAllByUserAndPlanStatus(User user, String status);

    Plan findByUserAndPlanId(User user, Long planId);

    Plan findByPlanId(Long planId);


    //List<Plan> findAllByUserAndPlanStatusNotIn(User user, String delete);   
}
