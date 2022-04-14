package com.example.tlover.domain.scrap.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.scrap.entity.Scrap;

import java.util.Optional;

public interface ScrapRepositoryCustom {
    Optional<Long> findScrapCountNotDeletedByDiary(Diary diary);
}
