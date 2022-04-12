package com.example.tlover.domain.diary_img.entity;

import com.example.tlover.domain.diary.dto.CreateDiaryRequest;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryImg {

    @Id
    @GeneratedValue
    private Long diaryImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_diaryId")
    private Diary diary;

    private String diaryImg;

    public void setAnnonce(Diary diary) {
        this.diary = diary;
        diary.getDiaryImgs().add(this);
    }

    public static DiaryImg toEntity(String diaryImgFileName , Diary diary){
        DiaryImg diaryImg = DiaryImg.builder()
                .diaryImg(diaryImgFileName).build();
        diaryImg.setDiary(diary);
        return diaryImg;
    }

}
