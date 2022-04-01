package com.example.trover.domain.reply.entity;

import com.example.trover.domain.annonce.entity.Annonce;
import com.example.trover.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reply {

    @Id
    @GeneratedValue
    private Long replyId;

    private LocalDateTime replyTime;

    private String replyState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annonce_annonceId")
    private Annonce annonce;

    public void setUser(User user) {
        this.user = user;
        user.getReplies().add(this);
    }

    public void setAnnonce(User user) {
        this.annonce = annonce;
        annonce.getReplies().add(this);
    }

}
