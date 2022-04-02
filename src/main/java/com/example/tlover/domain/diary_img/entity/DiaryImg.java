package com.example.tlover.domain.diary_img.entity;

import com.example.tlover.domain.diary.entity.Diary;
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





}
