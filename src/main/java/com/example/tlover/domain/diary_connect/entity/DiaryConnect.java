package com.example.tlover.domain.diary_connect.entity;

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
public class DiaryConnect {

    @Id
    @GeneratedValue
    private Long diaryConnectId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_diaryId")
    private Diary diary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    private String diaryConnectTime;

    public void setDiary(Diary diary){
        this.diary = diary;
        diary.getDiaryConnect();
    }

    public void setUser(User user){
        this.user = user;
        user.getDiaryConnect();
    }
}
