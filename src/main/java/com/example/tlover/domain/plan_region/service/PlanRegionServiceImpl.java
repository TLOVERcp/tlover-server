package com.example.tlover.domain.plan_region.service;


import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.example.tlover.domain.plan_region.repository.PlanRegionRepository;
import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.validation.Valid;


@Service
@RequiredArgsConstructor
public class PlanRegionServiceImpl implements PlanRegionService {

    private final PlanRegionRepository planRegionRepository;
    private final RegionRepository regionRepository;

    @Override
    public void createPlanRegion(@Valid CreatePlanRequest createPlanRequest, Plan plan) {
        String[] regionName = createPlanRequest.getRegionName();
        for(String i : regionName) {
            Region region = regionRepository.findByRegionName(i).get();
            PlanRegion planRegion = PlanRegion.toEntity(region, plan);
            planRegionRepository.save(planRegion);
        }
    }
}