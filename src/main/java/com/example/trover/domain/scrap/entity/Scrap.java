package com.example.trover.domain.scrap.entity;

import com.example.trover.domain.annonce.entity.Annonce;
import com.example.trover.domain.user.entity.User;
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
public class Scrap {

    @Id
    @GeneratedValue
    private Long scrapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annonce_annonceId")
    private Annonce annonce;

}
