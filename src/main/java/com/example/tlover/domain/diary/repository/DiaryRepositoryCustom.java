package com.example.tlover.domain.diary.repository;

import com.example.tlover.domain.diary.dto.DiaryInquiryByLikedRankingResponse;
import com.example.tlover.domain.diary.dto.DiaryMyScrapOrLikedResponse;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryRepositoryCustom {

    Page<DiaryInquiryByLikedRankingResponse> findAllDiariesByLikedRanking(Pageable pageable);

    Page<DiaryMyScrapOrLikedResponse> findAllDiariesByMyLiked(Pageable pageable , User user);

    Page<DiaryMyScrapOrLikedResponse> findAllDiariesByMyScrap(Pageable pageable, User user);
}
