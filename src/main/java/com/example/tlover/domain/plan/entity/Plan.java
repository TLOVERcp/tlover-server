package com.example.tlover.domain.plan.entity;


import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.example.tlover.domain.user.entity.User;
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

    private String planTitle;

    private String planContext;

    private String planStatus;

    private LocalDateTime planStartDate;

    private LocalDateTime planEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @OneToMany(mappedBy = "plan")
    private List<PlanRegion> planRegions = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<Diary> diaries = new ArrayList<>();

    /**
     * 연관관계 메서드
     */

    public void setUser(User user) {
        this.user = user;
        user.getPlans().add(this);
    }

}
