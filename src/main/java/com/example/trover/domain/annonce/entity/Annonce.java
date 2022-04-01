package com.example.trover.domain.annonce.entity;

import com.example.trover.domain.annonce_img.entity.AnnonceImg;
import com.example.trover.domain.annonce_region.entity.AnnonceRegion;
import com.example.trover.domain.annonce_thema.entity.AnnonceThema;
import com.example.trover.domain.authority_annonce.entity.AuthorityAnnonce;
import com.example.trover.domain.authority_plan.entity.AuthorityPlan;
import com.example.trover.domain.reply.entity.Reply;
import com.example.trover.domain.user.entity.User;
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
public class Annonce {

    @Id
    @GeneratedValue
    private Long annonceId;

    private String annonceTitle;

    private String  annonceThema;

    private String  annoncePhoto;

    private String  annoncePublicStatus;

    private String  annonceArea;

    private String annonceStatus;

    private String annonceContext;

    private String  annonceStartDate;

    private String  annonceWriteDate;

    private String  annonceEndDate;

    private String  annonceView;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @OneToMany(mappedBy = "annonce")
    private List<AuthorityAnnonce> authorityAnnonces = new ArrayList<>();

    @OneToMany(mappedBy = "annonce")
    private List<AuthorityPlan> authorityPlans = new ArrayList<>();

    @OneToMany(mappedBy = "annonce")
    private List<AnnonceImg> annonceImgs = new ArrayList<>();

    @OneToMany(mappedBy = "annonce")
    private List<AnnonceRegion> annonceRegions = new ArrayList<>();

    @OneToMany(mappedBy = "annonce")
    private List<AnnonceThema> annonceThemas = new ArrayList<>();

    @OneToMany(mappedBy = "annonce")
    private List<Reply> replies = new ArrayList<>();

    /**
     * 연관관계 메서드
     */

    public void setUser(User user) {
        this.user = user;
        user.getAnnonces().add(this);
    }



}
