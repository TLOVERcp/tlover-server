package com.example.trover.domain.annonce_thema.entity;

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
public class AnnonceThema {

    @Id @GeneratedValue
    private Long annonceThemaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annonce_annonceId")
    private Annonce annonce;

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
        annonce.getAnnonceThemas().add(this);
    }





}
