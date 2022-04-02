package com.example.tlover.domain.diary.entity;

import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.authority_plan.entity.AuthorityPlan;
import com.example.tlover.domain.diary_img.entity.DiaryImg;
import com.example.tlover.domain.diary_region.entity.DiaryRegion;
import com.example.tlover.domain.diary_thema.entity.DiaryThema;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.reply.entity.Reply;
import com.example.tlover.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Diary {
    @Id
    @GeneratedValue
    private Long diaryId;

    private String diaryTitle;

    private String  diaryPublicStatus;

    private String diaryStatus;

    private String diaryContext;

    private String  diaryStartDate;

    private String  diaryWriteDate;

    private String  diaryEndDate;

    private String  diaryView;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_planId")
    private Plan plan;

    @OneToMany(mappedBy = "diary")
    private List<AuthorityDiary> authoritydiarys = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<AuthorityPlan> authorityPlans = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<DiaryImg> diaryImgs = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<DiaryRegion> diaryRegions = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<DiaryThema> diaryThemas = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<Reply> replies = new ArrayList<>();

    /**
     * 연관관계 메서드
     */

//    public void setUser(User user) {
//        this.user = user;
//        user.getDiaries().add(this);
//    }
//
//    public void setPlan(Plan plan) {
//        this.plan = plan;
//        plan.getDiaries().add(this);
//    }



}
