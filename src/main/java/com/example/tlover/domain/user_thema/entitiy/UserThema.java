package com.example.tlover.domain.user_thema.entitiy;

import com.example.tlover.domain.thema.entity.Thema;
import com.example.tlover.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserThema {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userThemaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thema_themaId")
    private Thema thema;

    public void setUser(User user) {
        this.user = user;
        user.getUserThemas().add(this);
    }

    public void setThema(Thema thema) {
        this.thema = thema;
        thema.getUserThemas().add(this);
    }







}
