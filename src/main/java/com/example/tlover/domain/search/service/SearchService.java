package com.example.tlover.domain.search.service;

import com.example.tlover.domain.search.dto.DiarySearchResponse;
import com.example.tlover.domain.search.dto.UserSearchResponse;
import com.example.tlover.global.dto.PaginationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {

    PaginationDto<List<DiarySearchResponse>> getSearchedDiary(String keyword, Long userId, Pageable pageable);

    UserSearchResponse getSearchedUser(String keyword);


}
