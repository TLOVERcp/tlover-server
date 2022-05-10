package com.example.tlover.domain.scrap.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.scrap.dto.DiaryInquiryByScrapRankingResponse;
import com.example.tlover.domain.scrap.entity.Scrap;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ScrapRepositoryCustom {
    Optional<Long> findScrapCountNotDeletedByDiary(Diary diary);
    Optional<Scrap> findByUserAndDiaryAndNotDeleted(User user, Diary diary);
    Page<DiaryInquiryByScrapRankingResponse> findAllDiariesByScrapRanking(Pageable pageable);

}

