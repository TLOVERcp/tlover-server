package com.example.trover.domain.plan.repository;

import com.example.trover.domain.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
