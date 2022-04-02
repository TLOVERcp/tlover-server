package com.example.tlover.domain.user.entity;

import com.example.tlover.domain.authority_plan.entity.AuthorityPlan;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.reply.entity.Reply;
import com.example.tlover.domain.report.entity.Report;
import com.example.tlover.domain.scrap.entity.Scrap;
import com.example.tlover.domain.user.constant.UserConstants.EOAuth2UserServiceImpl;
import com.example.tlover.domain.user.constant.UserConstants.ESocialProvider;
import com.example.tlover.domain.user_refreshtoken.entity.UserRefreshToken;
import com.example.tlover.domain.user_region.entity.UserRegion;
import com.example.tlover.domain.user_thema.entitiy.UserThema;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

//    @OneToMany(mappedBy = "user")
//    private List<Annonce> annonces = new ArrayList<>();

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

//    @OneToMany(mappedBy = "user")
//    private List<AuthorityAnnonce> authorityAnnonces = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AuthorityPlan> authorityPlans = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Reply> replies = new ArrayList<>();


    /**
     * 연관관계 메서드
     */

//    public void addAnnonce(Annonce annonce) {
//        this.annonces.add(annonce);
//        annonce.setUser(this);
//    }

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
                .userLoginId(ESocialProvider.eNaver + userInfo.get(EOAuth2UserServiceImpl.eNaverEmailAttribute.getValue()).toString())
                .userEmail(userInfo.get(EOAuth2UserServiceImpl.eNaverEmailAttribute.getValue()).toString())
                .userNickName(userInfo.get(EOAuth2UserServiceImpl.eNaverNameAttribute.getValue()).toString())
                .userProfileImg(userInfo.get(EOAuth2UserServiceImpl.eNaverProfileImageAttribute.getValue()).toString())
                .userSocialProvider(ESocialProvider.eNaver)
                .build();
    }
}
