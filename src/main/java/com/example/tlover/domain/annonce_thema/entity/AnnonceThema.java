package com.example.tlover.domain.annonce_thema.entity;

import com.example.tlover.domain.annonce.entity.Annonce;
import com.example.tlover.domain.thema.entity.Thema;
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
public class AnnonceThema {

    @Id @GeneratedValue
    private Long annonceThemaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annonce_annonceId")
    private Annonce annonce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thema_themaId")
    private Thema thema;

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
        annonce.getAnnonceThemas().add(this);
    }

    public void setThema(Thema thema) {
        this.thema = thema;
        thema.getAnnonceThemas().add(this);
    }






}
