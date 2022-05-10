package com.example.tlover.domain.diary.entity;

import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.diary.dto.CreateDiaryRequest;
import com.example.tlover.domain.diary.dto.ModifyDiaryRequest;
import com.example.tlover.domain.diary_connection.entity.DiaryConnection;
import com.example.tlover.domain.diary_context.entity.DiaryContext;
import com.example.tlover.domain.diary_region.entity.DiaryRegion;
import com.example.tlover.domain.diary_thema.entity.DiaryThema;
import com.example.tlover.domain.diray_liked.entity.DiaryLiked;
import com.example.tlover.domain.history.entity.History;
import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.reply.entity.Reply;
import com.example.tlover.domain.scrap.entity.Scrap;
import com.example.tlover.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;
    private String diaryTitle;
    private String diaryStatus;

    private String diaryView;

    private String diaryStartDate;

    private String diaryEndDate;

    private String diaryWriteDate;

    private int diaryPlanDays;

    private int totalCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_planId")
    private Plan plan;

    @OneToMany(mappedBy = "diary", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<MyFile> myFiles = new ArrayList<>();

    @OneToMany(mappedBy = "diary" , orphanRemoval = true , cascade = CascadeType.PERSIST)
    private List<DiaryContext> diaryContexts = new ArrayList<>();

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

    public void getTime(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime startDate = LocalDateTime.parse(getDiaryStartDate(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(getDiaryEndDate(), formatter);

        System.out.println((endDate.getDayOfMonth()- startDate.getDayOfMonth()) +1);

    }

    public static Diary toEntity(CreateDiaryRequest createDiaryRequest , User user , Plan plan){
        Diary diary = new Diary();
           diary.setDiaryTitle(createDiaryRequest.getDiaryTitle());
           diary.setDiaryWriteDate(LocalDateTime.now().toString());
           diary.setDiaryStartDate(createDiaryRequest.getDiaryStartDate().toString());
           diary.setDiaryEndDate(createDiaryRequest.getDiaryEndDate().toString());
           diary.setTotalCost(createDiaryRequest.getTotalCost());
           diary.setDiaryPlanDays(plan.getPlanEndDate().getDayOfMonth() - plan.getPlanStartDate().getDayOfMonth()+1);
           diary.setDiaryStatus("ACTIVE");
           diary.setUser(user);
           diary.setPlan(plan);
        return diary;
}

    public static Diary updateDiary(ModifyDiaryRequest modifyDiaryRequest, Plan plan){
        Diary diary = new Diary();
            diary.setDiaryTitle(modifyDiaryRequest.getDiaryTitle());
            diary.setDiaryStartDate(modifyDiaryRequest.getDiaryStartDate().toString());
            diary.setDiaryEndDate(modifyDiaryRequest.getDiaryEndDate().toString());
            diary.setDiaryWriteDate(LocalDateTime.now().toString());
            diary.setPlan(plan);
        return diary;
    }



}
