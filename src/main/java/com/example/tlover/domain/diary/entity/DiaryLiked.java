package com.example.tlover.domain.diary.entity;

import com.example.tlover.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryLiked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryLikedId;

    private boolean isLiked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_diaryId")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    public void setDiary(Diary diary) {
        this.diary = diary;
        diary.getDiaryLikeds().add(this);
    }

    public void setUser(User user) {
        this.user = user;
        user.getDiaryLikeds().add(this);
    }

    public static DiaryLiked toEntity(User user , Diary diary){
        DiaryLiked diaryLiked = DiaryLiked.builder()
                .isLiked(true)
                .build();
        diaryLiked.setDiary(diary);
        diaryLiked.setUser(user);
        return diaryLiked;
    }

}
