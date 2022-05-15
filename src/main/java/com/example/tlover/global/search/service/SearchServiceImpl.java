package com.example.tlover.global.search.service;


import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.region.repository.RegionRepository;
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
    private final RegionRepository regionRepository;

    /**
     * 다이어리 검색 조회
     * @return PaginationDto<List<DiarySearchResponse>>
     * @author 윤여찬
     */
    @Override
    public PaginationDto<List<DiarySearchResponse>> getSearchedDiary(String keyword, Pageable pageable) {

        Page<DiarySearchResponse> page;
        Thema thema = themaRepository.findByThemaName(keyword);
        Region region = regionRepository.findByRegionName(keyword).get();

        // 키워드가 테마이름인지 지역이름인지 확인
        if (thema != null) {
            page = this.searchRepository.findDiaryByThema(keyword, pageable);
        } else if (region != null) {
            page = this.searchRepository.findDiaryByRegion(keyword, pageable);
        } else {
            page = this.searchRepository.findDiaryByKeyword(keyword, pageable);
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
    public UserSearchResponse getSearchedUser(String keyword) {

        List<UserSearchResponse> userSearchResponse = this.searchRepository.findUserByKeyword(keyword);

        if (userSearchResponse.isEmpty()) throw new NotFoundSearchUserException();

        return userSearchResponse.get(0);
    }

}