package com.example.tlover.global.search.service;

import com.example.tlover.global.dto.PaginationDto;
import com.example.tlover.global.search.dto.DiarySearchResponse;
import com.example.tlover.global.search.dto.UserSearchResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {

    PaginationDto<List<DiarySearchResponse>> getSearchedDiary(String keyword, Pageable pageable);

    PaginationDto<List<UserSearchResponse>> getSearchedUser(String keyword, Pageable pageable);


}
