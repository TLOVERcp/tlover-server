package com.example.tlover.domain.diray_liked.entity;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.thema.entity.Thema;
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

    @Id @GeneratedValue
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
