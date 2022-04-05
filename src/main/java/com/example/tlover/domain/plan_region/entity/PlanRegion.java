package com.example.tlover.domain.plan_region.entity;

import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.region.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanRegion {

    @Id @GeneratedValue
    private Long planRegionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_planId")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_regionId")
    private Region region;

    public void setPlan(Plan plan) {
        this.plan = plan;
        plan.getPlanRegions().add(this);
    }

    public void setRegion(Region region) {
        this.region = region;
        region.getPlanRegions().add(this);
    }
    public static PlanRegion toEntity(Region region, Plan plan){
        PlanRegion p = new PlanRegion();
        p.setPlan(plan);
        p.setRegion(region);
        return p;
    }

}
