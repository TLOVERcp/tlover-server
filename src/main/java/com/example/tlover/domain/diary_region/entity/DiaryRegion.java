package com.example.tlover.domain.diary_region.entity;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.region.entity.Region;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryRegion {



    @Id
    @GeneratedValue
    private Long diaryRegionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_diaryId")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_regionId")
    private Region region;

    public void setDiary(Diary diary) {
        this.diary = diary;
        diary.getDiaryRegions().add(this);
    }

    public void setRegion(Region region) {
        this.region = region;
        region.getDiaryRegions().add(this);
    }

    public static DiaryRegion toEntity(Region region , Diary diary) {
        DiaryRegion diaryRegion = new DiaryRegion();
        diaryRegion.setDiary(diary);
        diaryRegion.setRegion(region);
        return diaryRegion;
    }


}
