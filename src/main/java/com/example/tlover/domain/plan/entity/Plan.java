package com.example.tlover.domain.plan.entity;


import com.example.tlover.domain.authority_plan.entity.AuthorityPlan;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.example.tlover.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long planId;

    private String planTitle;

    private String planContext;

    private String planStatus;

    private String planStartDate;

    private String planEndDate;

    private String planWriteDate;

    private Long expense;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @OneToMany(mappedBy = "plan")
    private List<PlanRegion> planRegions = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<AuthorityPlan> authorityPlans = new ArrayList<>();

//    @OneToOne(mappedBy = "plan")
//    private DiaryState diaryState;

    public void setUser(User user) {
        this.user = user;
        user.getPlans().add(this);
    }


    public static Plan toEntity(CreatePlanRequest createPlanRequest, User user) {
        Plan plan = new Plan();
        plan.setPlanTitle(createPlanRequest.getPlanTitle());
        plan.setPlanContext(createPlanRequest.getPlanContext());
        plan.setPlanStartDate(createPlanRequest.getPlanStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        plan.setPlanEndDate(createPlanRequest.getPlanEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        plan.setPlanWriteDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        plan.setPlanStatus("ACTIVE");
        plan.setUser(user);
        plan.setExpense(createPlanRequest.getExpense());
        return plan;


    }

    public static Plan updatePlan(CreatePlanRequest createPlanRequest, Plan plan) {
        plan.setPlanTitle(createPlanRequest.getPlanTitle());
        plan.setPlanContext(createPlanRequest.getPlanContext());
        plan.setPlanStartDate(createPlanRequest.getPlanStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        plan.setPlanEndDate(createPlanRequest.getPlanEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        plan.setPlanWriteDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        plan.setPlanStatus("ACTIVE");
        return plan;
    }
}