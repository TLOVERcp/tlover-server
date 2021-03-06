package com.example.tlover.domain.diary.entity;

import com.example.tlover.domain.authority.entity.AuthorityDiary;
import com.example.tlover.domain.diary.dto.CreateDiaryRequest;
import com.example.tlover.domain.history.entity.History;
import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.reply.entity.Reply;
import com.example.tlover.domain.scrap.entity.Scrap;
import com.example.tlover.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    private String diaryTitle;

    private String diaryContext;

    private String diaryStatus;

    private String diaryView;

    private String diaryStartDate;

    private String diaryEndDate;

    private String diaryWriteDate;

    private String diaryRegionDetail;

    private int totalCost;

    private int diaryPlanDays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_planId")
    private Plan plan;

    @OneToMany(mappedBy = "diary")
    @Builder.Default
    private List<MyFile> myFiles = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<AuthorityDiary> authoritydiarys = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<DiaryRegion> diaryRegions = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<DiaryThema> diaryThemas = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<DiaryLiked> diaryLikeds = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "diary", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<DiaryConnection> diaryConnections = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
        user.getDiaries().add(this);
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
        plan.getDiaries().add(this);
    }

    public static Diary toEntity(String regionDetail, CreateDiaryRequest createDiaryRequest, User user, Plan plan , int diaryPlanDays){
        Diary diary = new Diary();
           diary.setDiaryTitle(createDiaryRequest.getDiaryTitle());
           diary.setDiaryContext(createDiaryRequest.getDiaryContext());
           diary.setDiaryWriteDate(LocalDateTime.now().toString());
           diary.setDiaryStartDate(createDiaryRequest.getDiaryStartDate());
           diary.setDiaryEndDate(createDiaryRequest.getDiaryEndDate());
           diary.setDiaryPlanDays(diaryPlanDays);
           diary.setTotalCost(createDiaryRequest.getTotalCost());
           diary.setDiaryRegionDetail(regionDetail);
           diary.setDiaryStatus("ACTIVE");
           diary.setUser(user);
           diary.setPlan(plan);
        return diary;
}

}
