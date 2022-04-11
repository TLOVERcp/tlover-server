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
}
