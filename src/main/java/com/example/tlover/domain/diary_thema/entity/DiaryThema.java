package com.example.tlover.domain.diary_thema.entity;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.thema.entity.Thema;
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
public class DiaryThema {

    @Id @GeneratedValue
    private Long diaryThemaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_diaryId")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thema_themaId")
    private Thema thema;

    public void setAnnonce(Diary diary) {
        this.diary = diary;
        diary.getDiaryThemas().add(this);
    }

    public void setThema(Thema thema) {
        this.thema = thema;
        thema.getDiaryThemas().add(this);
    }






}
