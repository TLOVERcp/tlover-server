package com.example.tlover.domain.history.entity;


import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_diaryId")
    private Diary diary;

    public void setUser(User user) {
        this.user = user;
        user.getHistories().add(this);
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
        diary.getHistories().add(this);
    }

    public static History toEntity(User user, Diary diary, LocalDateTime localDateTime) {
        return History.builder()
                .user(user)
                .diary(diary)
                .date(localDateTime)
                .build();
    }

}
