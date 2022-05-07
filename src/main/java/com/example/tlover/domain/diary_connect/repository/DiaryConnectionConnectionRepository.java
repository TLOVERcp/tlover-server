package com.example.tlover.domain.diary_connect.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary_connect.entity.DiaryConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryConnectionConnectionRepository extends JpaRepository<DiaryConnection, Long> {
    Optional<List<DiaryConnection>> findByDiary_DiaryId(Long diaryId);
    @Query("select count(*) from DiaryConnection d where d.diary = :diary")
    Optional<Integer> findConnectionCountByDiary(Diary diary);
}
