package com.example.tlover.global.search.service;


import com.example.tlover.domain.thema.entity.Thema;
import com.example.tlover.domain.thema.repository.ThemaRepository;
import com.example.tlover.global.dto.PaginationDto;
import com.example.tlover.global.search.dto.DiarySearchResponse;
import com.example.tlover.global.search.dto.UserSearchResponse;
import com.example.tlover.global.search.exception.NotFoundSearchDiaryException;
import com.example.tlover.global.search.exception.NotFoundSearchUserException;
import com.example.tlover.global.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;
    private final ThemaRepository themaRepository;

    /**
     * 다이어리 검색 조회
     * @return PaginationDto<List<DiarySearchResponse>>
     * @author 윤여찬
     */
    @Override
    public PaginationDto<List<DiarySearchResponse>> getSearchedDiary(String keyword, Pageable pageable) {

        Page<DiarySearchResponse> page;
        Thema thema = themaRepository.findByThemaName(keyword);

        // 키워드가 테마이름인지 확인
        if (thema != null) {
            page = this.searchRepository.findThemaByKeword(keyword, pageable);
        } else {
            page = this.searchRepository.findDiaryByKeywordCustom(keyword, pageable);
        }

        List<DiarySearchResponse> data = page.get().collect(Collectors.toList());

        for (DiarySearchResponse diary : data) {
            List<String> themaNames = searchRepository.findThemaNamesByDiaryId(diary.getDiaryId());
            List<String> regionNames = searchRepository.findRegionNamesByDiaryId(diary.getDiaryId());
            if (themaNames != null) diary.setThemaNames(themaNames);
            if (regionNames != null) diary.setRegionNames(regionNames);
        }

        if (data.isEmpty()) throw new NotFoundSearchDiaryException();

        return PaginationDto.of(page, data);
    }

    @Override
    public PaginationDto<List<UserSearchResponse>> getSearchedUser(String keyword, Pageable pageable) {

        Page<UserSearchResponse> page = this.searchRepository.findUserByKeyword(keyword, pageable);
        List<UserSearchResponse> data = page.get().collect(Collectors.toList());

        if (data.isEmpty()) throw new NotFoundSearchUserException();

        return PaginationDto.of(page, data);
    }

}