package com.example.trover.domain.user_thema.entitiy;

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
public class UserThema {

    @Id
    @GeneratedValue
    private Long userThemaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    public void setUser(User user) {
        this.user = user;
        user.getUserThemas().add(this);
    }







}
