package com.example.trover.domain.plan.entity;


import com.example.trover.domain.plan_region.entity.PlanRegion;
import com.example.trover.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue
    private Long planId;

    private String planPhoto;

    private String planArea;

    private String planTitle;

    private String planThema;

    private LocalDateTime planStartDate;

    private LocalDateTime planEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @OneToMany(mappedBy = "plan")
    private List<PlanRegion> planRegions = new ArrayList<>();


    /**
     * 연관관계 메서드
     */

    public void setUser(User user) {
        this.user = user;
        user.getPlans().add(this);
    }

}
