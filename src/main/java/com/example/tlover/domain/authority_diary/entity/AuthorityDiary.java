package com.example.tlover.domain.authority_diary.entity;

import com.example.tlover.domain.diary.entity.Diary;
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
public class AuthorityDiary {

    @Id
    @GeneratedValue
    private Long authorityDiaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_diaryId")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    // private String authorityAnnonceRole;

    public void setUser(User user) {
        this.user = user;
        user.getAuthorityDiaries().add(this);
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
        diary.getAuthoritydiarys().add(this);
    }
}
