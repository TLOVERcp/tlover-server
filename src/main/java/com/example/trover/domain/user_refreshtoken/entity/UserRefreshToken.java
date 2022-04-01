package com.example.trover.domain.user_refreshtoken.entity;

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
public class UserRefreshToken {

    @Id
    @GeneratedValue
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
