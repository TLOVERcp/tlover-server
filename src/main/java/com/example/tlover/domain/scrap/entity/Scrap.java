package com.example.tlover.domain.scrap.entity;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapId;

    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_diaryId")
    private Diary diary;

    /**
     * 연관관계 메서드
     */

    public void setUser(User user) {
        this.user = user;
        user.getScraps().add(this);
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
        diary.getScraps().add(this);
    }

    public static Scrap toEntity(User user, Diary diary) {
        Scrap scrap = Scrap.builder()
                .isDeleted(false)
                .build();
        scrap.setUser(user);
        scrap.setDiary(diary);
        return scrap;
    }

}
