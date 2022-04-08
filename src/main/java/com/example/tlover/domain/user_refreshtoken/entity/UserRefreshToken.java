package com.example.tlover.domain.user_refreshtoken.entity;

import com.example.tlover.domain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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