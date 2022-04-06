package com.example.tlover.domain.plan_region.repository;

import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRegionRepository extends JpaRepository<PlanRegion, Long> {
    List<PlanRegion> findAllByPlan(Plan plan);
}