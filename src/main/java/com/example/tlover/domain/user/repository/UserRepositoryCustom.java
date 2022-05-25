package com.example.tlover.domain.user.repository;

import com.example.tlover.global.search.dto.DiarySearchResponse;
import com.example.tlover.global.search.dto.UserSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {

    List<String> findThemaNamesByUserId(Long userId);

    List<String> findRegionByUserId(Long userId);


}
