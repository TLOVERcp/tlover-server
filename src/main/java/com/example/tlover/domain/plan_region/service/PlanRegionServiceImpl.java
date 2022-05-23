package com.example.tlover.domain.plan_region.service;


import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.example.tlover.domain.plan_region.repository.PlanRegionRepository;
import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;


@Service
@RequiredArgsConstructor
public class PlanRegionServiceImpl implements PlanRegionService {
    private final PlanRegionRepository planRegionRepository;
    private final RegionRepository regionRepository;

    @Override
    public void createPlanRegion(CreatePlanRequest createPlanRequest, Plan plan) {
        String[] regionName = checkRegion(createPlanRequest.getRegionName());
        for(String i : regionName) {
            Region region = regionRepository.findByRegionName(i).get();
            PlanRegion planRegion = PlanRegion.toEntity(region, plan);
            planRegionRepository.save(planRegion);
        }
    }

    @Override
    @Transactional
    public void updatePlanRegion(CreatePlanRequest createPlanRequest, Plan plan) {
        planRegionRepository.deleteAllByPlan(plan);
        String[] regionName = createPlanRequest.getRegionName();
        for(String i : regionName) {
            Region region = regionRepository.findByRegionName(i).get();
            PlanRegion planRegion = PlanRegion.toEntity(region, plan);
            planRegionRepository.save(planRegion);
        }
    }

    @Override
    @Transactional
    public void deletePlanRegion(Plan plan) {
        planRegionRepository.deleteAllByPlan(plan);
    }

    public String[] checkRegion(String[] regionName){
        ArrayList<String> regions = new ArrayList<>();
        for(String s : regionName){
            if(s.equals("제주")||s.equals("서귀포")) regions.add("제주도");
            else if(s.equals("춘천")||s.equals("속초")||s.equals("강릉")) regions.add("강원도");
            else if(s.equals("서울")) regions.add("서울");
            else if(s.equals("인천")) regions.add("인천");
            else if(s.equals("양평")||s.equals("가평")||s.equals("파주")) regions.add("경기도");
            else if(s.equals("부산")||s.equals("거제")) regions.add("경상남도");
            else if(s.equals("안동")||s.equals("경주")||s.equals("포항")) regions.add("경상북도");
            else if(s.equals("태안")||s.equals("공주")||s.equals("보령")) regions.add("충청남도");
            else if(s.equals("단양")) regions.add("충청북도");
            else if(s.equals("여수")||s.equals("목포")||s.equals("순천")||s.equals("담양")) regions.add("전라남도");
            else if(s.equals("전주")) regions.add("전라북도");
            //else regions.add("");
        }
        HashSet<String> hashSet = new HashSet<>(regions);
        regions=new ArrayList<>(hashSet);
        String[] region = new String[regions.size()];
        for(int i=0; i< regions.size(); i++) {
            region[i] = regions.get(i);
        }
        return region;
    }
}
