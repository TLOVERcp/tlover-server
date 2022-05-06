package com.example.tlover.domain.diary_region.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary_img.entity.DiaryImg;
import com.example.tlover.domain.diary_region.entity.DiaryRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRegionRepository extends JpaRepository<DiaryRegion, Long> {

    void deleteByDiary_DiaryId(Long diaryId);

    List<DiaryRegion> findByDiary(Diary diary);
}
