package com.example.tlover.domain.rating.entity;

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
public class Rating {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long ratingId;

    private float rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;


    public static Rating toEntity(User user) {
        Rating rating = new Rating();
        rating.setUser(user);
        rating.setRating(0);
        return rating;
    }

    public void updateRating(long num, Rating rating) {
        rating.setRating((float) (num*0.1));

    }
}
