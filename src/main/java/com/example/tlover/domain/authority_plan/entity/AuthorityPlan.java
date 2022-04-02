package com.example.tlover.domain.authority_plan.entity;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.user.entity.User;
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
public class AuthorityPlan {

    @Id
    @GeneratedValue
    private Long authorityPlanId;
//plan으로 바꾸기
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_diaryId")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    public void setUser(User user) {
        this.user = user;
        user.getAuthorityPlans().add(this);
    }
//plan으로 바꾸기
    public void setAnnonce(Diary diary) {
        this.diary = diary;
        diary.getAuthorityPlans().add(this);
    }
}
