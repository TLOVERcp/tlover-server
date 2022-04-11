package com.example.tlover.domain.plan_region.repository;

import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRegionRepository extends JpaRepository<PlanRegion, Long> {
    Optional<List<PlanRegion>> findAllByPlan(Plan plan);
    void deleteAllByPlan(Plan plan);
}
