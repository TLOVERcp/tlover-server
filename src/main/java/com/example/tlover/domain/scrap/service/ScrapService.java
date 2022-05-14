package com.example.tlover.domain.scrap.service;

import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.scrap.dto.*;
import com.example.tlover.global.dto.PaginationDto;
import com.example.tlover.global.dto.ResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScrapService {

    ScrapCountResponse getScrapCount(Long diaryId);
    ScrapChangeResponse changeScrap(ScrapChangeRequest scrapChangeRequest, Long userId);
    ScrapOrNotResponse getScrapOrNot(ScrapOrNotRequest scrapOrNotRequest, Long userId);
    PaginationDto<List<DiaryInquiryByScrapRankingResponse>> getDiariesByScrapRanking(Pageable pageable);

}
