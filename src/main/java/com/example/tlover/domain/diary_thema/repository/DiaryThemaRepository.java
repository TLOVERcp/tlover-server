package com.example.tlover.domain.diary_thema.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary_thema.entity.DiaryThema;
import com.example.tlover.domain.thema.entity.Thema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryThemaRepository extends JpaRepository<DiaryThema, Long> {
    void deleteByDiary_DiaryId(Long diaryId);

    List<DiaryThema> findByThema(Thema thema);

    List<DiaryThema> findByThemaThemaId(Long themaId);
}
