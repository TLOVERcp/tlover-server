package com.example.tlover.global.search.repository;

import com.example.tlover.global.search.dto.DiarySearchResponse;
import com.example.tlover.global.search.dto.UserSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRepositoryCustom {

    Page<DiarySearchResponse> findThemaByKeword(String keyword, Pageable pageable);

    Page<DiarySearchResponse> findDiaryByKeywordCustom(String keyword, Pageable pageable);

    List<String> findThemaNamesByDiaryId(Long diaryId);

    List<String> findRegionNamesByDiaryId(Long diaryId);

    List<UserSearchResponse> findUserByKeyword(String keyword);
}
