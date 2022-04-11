package com.example.tlover.domain.diary.repository;

import com.example.tlover.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findBy();
}
