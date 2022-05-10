package com.example.tlover.domain.diary_context.entity;

import com.example.tlover.domain.diary.entity.Diary;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryContext {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryContextId;

    private String context;

    private int diaryDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="diary_diaryId")
    private Diary diary;

    public void setDiary(Diary diary) {
        this.diary = diary;
        diary.getDiaryContexts().add(this);
    }

    public static DiaryContext toEntity(String context , int diaryDay, Diary diary) {
        DiaryContext diaryContext = DiaryContext.builder()
                .context(context)
                .diaryDay(diaryDay).build();
        diaryContext.setDiary(diary);
        return diaryContext;
    }

}
