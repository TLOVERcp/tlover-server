package com.example.tlover.domain.plan.repository;

import com.example.tlover.domain.plan.dto.PlanListResponse;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("from Plan where user = :user and planStatus not in ('DELETE')")
    Optional<List<Plan>> findAllByUser(@Param("user") User user);

    Optional<List<Plan>> findAllByUserAndPlanStatus(User user, String status);

    Optional<Plan> findByUserAndPlanId(User user, Long planId);

    Optional<Plan> findByPlanId(Long planId)
            ;
}
