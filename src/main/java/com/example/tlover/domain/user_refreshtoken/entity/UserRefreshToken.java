package com.example.tlover.domain.user_refreshtoken.entity;

import com.example.tlover.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRefreshToken {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long userRefreshtokenId;

    private String userRefreshtokenToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    public void setUser(User user) {
        this.user = user;
        user.getUserRefreshTokens().add(this);
    }

}
