package com.example.trover.domain.plan_region.entity;

import com.example.trover.domain.plan.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    public void setPlan(Plan plan) {
        this.plan = plan;
        plan.getPlanRegions().add(this);
    }


}
