package com.example.tlover.domain.authority_annonce.entity;

import com.example.tlover.domain.annonce.entity.Annonce;
import com.example.tlover.domain.user.entity.User;
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
public class AuthorityAnnonce {

    @Id
    @GeneratedValue
    private Long authorityAnnonceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annonce_annonceId")
    private Annonce annonce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

   // private String authorityAnnonceRole;

    public void setUser(User user) {
        this.user = user;
        user.getAuthorityAnnonces().add(this);
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
        annonce.getAuthorityAnnonces().add(this);
    }



}
