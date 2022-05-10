package com.example.tlover.domain.diary_context.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary_context.entity.DiaryContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryContextRepository extends JpaRepository<DiaryContext, Long> {
    void deleteByDiary_DiaryId(Long diaryId);
    Optional<DiaryContext> findByDiaryAndDiaryDay(Diary diary, int diaryDay);

}
