package com.example.trover.domain.annonce_img.entity;

import com.example.trover.domain.annonce.entity.Annonce;
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
public class AnnonceImg {

    @Id
    @GeneratedValue
    private Long annonceimg_annonceImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annonce_annonceId")
    private Annonce annonce;

    private String annonceImg;

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
        annonce.getAnnonceImgs().add(this);
    }





}
