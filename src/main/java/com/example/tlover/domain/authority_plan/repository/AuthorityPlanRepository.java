package com.example.tlover.domain.authority_plan.repository;

import com.example.tlover.domain.authority_plan.entity.AuthorityPlan;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorityPlanRepository extends JpaRepository<AuthorityPlan , Long> {

    Optional<List<AuthorityPlan>> findAllByUser(User user);

    Optional<List<AuthorityPlan>> findAllByPlan(Plan plan);

    void deleteAllByPlan(Plan plan);

    Optional<AuthorityPlan> findByAuthorityPlanId(Long authorityPlanId);

    Optional<List<AuthorityPlan>> findAllByUserAndAuthorityPlanStatus(User user, String status);

    Optional<List<AuthorityPlan>> findAllByPlanAndAuthorityPlanStatus(Plan plan, String accept);

    Optional<AuthorityPlan> findAllByPlanAndUser(Plan plan, User user);

   // @Query("select a from AuthorityPlan a where a.user = :user and a.authorityPlanStatus not in ('REJECT')")
    //Optional<AuthorityPlan> findByUser(User user);

    Optional<List<AuthorityPlan>> findAllByUserAndPlan(User user, Plan plan);

    Optional<AuthorityPlan> findByPlanPlanIdAndUser(Long planId, User user);
}
