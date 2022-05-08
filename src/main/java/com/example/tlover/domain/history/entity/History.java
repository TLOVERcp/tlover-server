package com.example.tlover.domain.history.entity;


import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.history.dto.CreateHistoryRequest;
import com.example.tlover.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue
    private Long historyId;

    private String date;

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

    public static History toEntity(CreateHistoryRequest createHistoryRequest, User user, Diary diary) {
        return History.builder()
                .user(user)
                .diary(diary)
                .date(createHistoryRequest.getDate().toString())
                .build();
    }

    public History updateEntity(String date) {
        this.date = date;
        return this;
    }
}
