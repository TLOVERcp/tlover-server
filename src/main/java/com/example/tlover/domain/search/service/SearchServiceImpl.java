package com.example.tlover.domain.search.service;


import com.example.tlover.domain.search.dto.DiarySearchResponse;
import com.example.tlover.domain.search.dto.UserSearchResponse;
import com.example.tlover.domain.search.repository.SearchRepository;
import com.example.tlover.domain.thema.entity.Thema;
import com.example.tlover.domain.thema.repository.ThemaRepository;
import com.example.tlover.global.dto.PaginationDto;
import com.example.tlover.domain.search.exception.NotFoundSearchDiaryException;
import com.example.tlover.domain.search.exception.NotFoundSearchUserException;
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
    public PaginationDto<List<DiarySearchResponse>> getSearchedDiary(String keyword, Long userId, Pageable pageable) {

        Page<DiarySearchResponse> page;
        Thema thema = themaRepository.findByThemaName(keyword);
        Page<DiarySearchResponse> region = this.searchRepository.findDiaryByRegion(keyword, pageable);

        // 키워드가 테마이름인지 지역이름인지 확인
        if (thema != null) {
            page = this.searchRepository.findDiaryByThema(keyword, pageable);
        } else if (!region.isEmpty()) {
            page = region;
        } else {
            page = this.searchRepository.findDiaryByKeyword(keyword, pageable);
        }

        List<DiarySearchResponse> data = page.get().collect(Collectors.toList());

        for (DiarySearchResponse diary : data) {
            List<String> themaNames = searchRepository.findThemaNamesByDiaryId(diary.getDiaryId());
            List<String> regionNames = searchRepository.findRegionDetailsByDiaryId(diary.getDiaryId());

            if (themaNames != null) diary.setThemaNames(themaNames);
            if (regionNames != null) diary.setRegionNames(regionNames);
            String fileKey = searchRepository.findDiaryImgByDiaryId(diary.getDiaryId());
            if (fileKey != null)
                diary.setDiaryImg(fileKey);
            diary.setScraped(searchRepository.findIsScrapedByUserId(userId, diary.getDiaryId()));
            diary.setLiked(searchRepository.findIsLikedByUserId(userId, diary.getDiaryId()));
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