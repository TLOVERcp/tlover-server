package com.example.tlover.domain.authority.entity;

import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityPlanId;

    private String authorityPlanShareDate;

    private String authorityPlanStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_planId")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    public void setUser(User user) {
        this.user = user;
        user.getAuthorityPlans().add(this);
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
        plan.getAuthorityPlans().add(this);
    }

    public static AuthorityPlan toEntity(Plan plan, User user, String status) {
        AuthorityPlan authorityPlan = new AuthorityPlan();
        authorityPlan.setPlan(plan);
        authorityPlan.setUser(user);
        authorityPlan.setAuthorityPlanStatus(status);
        authorityPlan.setAuthorityPlanShareDate(LocalDateTime.now().toString());
        return authorityPlan;

    }
}
