package com.example.tlover.domain.diary.repository;

import com.example.tlover.domain.diary.dto.DiaryInquiryByLikedRankingResponse;
import com.example.tlover.domain.diary.dto.DiarySearchResponse;
import com.example.tlover.domain.scrap.dto.DiaryInquiryByScrapRankingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiaryRepositoryCustom {

    Page<DiaryInquiryByLikedRankingResponse> findAllDiariesByLikedRanking(Pageable pageable);

    Page<DiarySearchResponse> findByThemaKewordCustom(String keyword, Pageable pageable);

    Page<DiarySearchResponse> findByKeywordCustom(String keyword, Pageable pageable);

    List<String> findThemaNamesByDiaryId(Long diaryId);

    List<String> findRegionNamesByDiaryId(Long diaryId);
}
