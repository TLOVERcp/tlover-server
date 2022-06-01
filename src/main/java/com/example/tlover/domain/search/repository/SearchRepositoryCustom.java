package com.example.tlover.domain.search.repository;

import com.example.tlover.domain.search.dto.DiarySearchResponse;
import com.example.tlover.domain.search.dto.UserSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRepositoryCustom {

    Page<DiarySearchResponse> findDiaryByThema(String keyword, Pageable pageable);

    Page<DiarySearchResponse> findDiaryByRegion(String keyword, Pageable pageable);

    Page<DiarySearchResponse> findDiaryByKeyword(String keyword, Pageable pageable);

    List<String> findThemaNamesByDiaryId(Long diaryId);

    List<String> findRegionDetailsByDiaryId(Long diaryId);

    List<UserSearchResponse> findUserByKeyword(String keyword);

    String findDiaryImgByDiaryId(Long diaryId);

    boolean findIsScrapedByUserId(Long userId, Long diaryId);

    boolean findIsLikedByUserId(Long userId, Long diaryId);
}
