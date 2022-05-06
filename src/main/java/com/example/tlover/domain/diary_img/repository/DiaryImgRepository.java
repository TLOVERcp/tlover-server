package com.example.tlover.domain.diary_img.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary_img.entity.DiaryImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryImgRepository extends JpaRepository<DiaryImg, Long> {
    void deleteByDiary_DiaryId(Long diaryId);

    List<DiaryImg> findByDiary(Diary diary);
}
