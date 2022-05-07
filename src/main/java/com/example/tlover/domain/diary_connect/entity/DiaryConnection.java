package com.example.tlover.domain.diary_connect.entity;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;

import static com.example.tlover.domain.user.constant.UserConstants.EOAuth2UserServiceImpl.*;
import static com.example.tlover.domain.user.constant.UserConstants.ESocialProvider.eKakao;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryConnection extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryConnectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_diaryId")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    /**
     * 엔티티 성성 메서드
     */
    public static DiaryConnection toEntity(Diary diary, User user) {
        DiaryConnection diaryConnection = DiaryConnection.builder()
                .build();
        diaryConnection.setDiary(diary);
        diaryConnection.setUser(user);
        return diaryConnection;
    }

    /**
     * 양방향 연관관계
     */
    public void setDiary(Diary diary){
        this.diary = diary;
        diary.getDiaryConnections().add(this);
    }

    public void setUser(User user){
        this.user = user;
        user.getDiaryConnections().add(this);
    }



}
