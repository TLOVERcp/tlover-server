package com.example.tlover.domain.user.entity;

import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.authority_plan.entity.AuthorityPlan;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.reply.entity.Reply;
import com.example.tlover.domain.report.entity.Report;
import com.example.tlover.domain.scrap.entity.Scrap;

import com.example.tlover.domain.user.constant.UserConstants.*;


import com.example.tlover.domain.user_refreshtoken.entity.UserRefreshToken;
import com.example.tlover.domain.user_region.entity.UserRegion;
import com.example.tlover.domain.user_thema.entitiy.UserThema;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.example.tlover.domain.user.constant.UserConstants.EOAuth2UserServiceImpl.*;
import static com.example.tlover.domain.user.constant.UserConstants.ESocialProvider.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    private String userLoginId;

    private String userPassword;

    private String userEmail;

    private String userProfileImg;

    private String userRating;

    private String userPhoneNum;

    private String userState;

    private String userNickName;

    @Enumerated(EnumType.STRING)
    private ESocialProvider userSocialProvider;

    @OneToMany(mappedBy = "user")
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Plan> plans = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserRefreshToken> userRefreshTokens = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserRegion> userRegions = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserThema> userThemas = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AuthorityDiary> authorityDiaries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AuthorityPlan> authorityPlans = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<MyFile> myFiles = new ArrayList<>();

    /**
     * 연관관계 메서드
     */

    public void addDiary(Diary diary) {
        this.diaries.add(diary);
        diary.setUser(this);
    }

    public void addPlan(Plan plan) {
        this.plans.add(plan);
        plan.setUser(this);
    }

    public void addScrap(Scrap scrap) {
        this.scraps.add(scrap);
        scrap.setUser(this);
    }

    public void addReport(Report report) {
        this.reports.add(report);
        report.setUser(this);
    }

    public void setMyFiles(List<MyFile> myFiles) {
        this.myFiles = myFiles;
        for (MyFile myFile : myFiles) {
            myFile.setUser(this);}
    }


    /**
     * Naver User Update
     */

    public User updateNaverUser(String userNickName, String userProfileImg) {
        this.userNickName = userNickName;
        this.userProfileImg = userProfileImg;
        return this;
    }

    public static User toEntityOfNaverUser(HashMap<String, Object> userInfo) {
        return User.builder()
                .userLoginId(eNaver + userInfo.get(eEmailAttribute.getValue()).toString())
                .userEmail(userInfo.get(eEmailAttribute.getValue()).toString())
                .userNickName(userInfo.get(eNameAttribute.getValue()).toString())
                .userProfileImg(userInfo.get(eNaverProfileImageAttribute.getValue()).toString())
                .userSocialProvider(eNaver)
                .build();
    }

    public User updateKakaoUser(String userNickName, String userProfileImg) {
        this.userNickName = userNickName;
        this.userProfileImg = userProfileImg;
        return this;
    }

    public static User toEntityOfKakaoUser(HashMap<String, Object> userInfo) {
        return User.builder()
                .userLoginId(eKakao + userInfo.get(eEmailAttribute.getValue()).toString())
                .userEmail(userInfo.get(eEmailAttribute.getValue()).toString())
                .userNickName(userInfo.get(eNameAttribute.getValue()).toString())
                .userProfileImg(userInfo.get(eKakaoProfileImageAttribute).toString())
                .userSocialProvider(eKakao)
                .build();
    }

    public static User toEntityOfGoogleUser(HashMap<String, Object> userInfo) {
        return User.builder()
                .userLoginId(eGoogle + userInfo.get(eEmailAttribute.getValue()).toString())
                .userEmail(userInfo.get(eEmailAttribute.getValue()).toString())
                .userNickName(userInfo.get(eNameAttribute.getValue()).toString())
                .userProfileImg(userInfo.get(eGoogleProfileImageAttribute.getValue()).toString())
                .userSocialProvider(eGoogle)
                .build();
    }

}
