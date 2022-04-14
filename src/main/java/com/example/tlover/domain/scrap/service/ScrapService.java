package com.example.tlover.domain.scrap.service;

import com.example.tlover.domain.scrap.dto.ScrapChangeRequest;
import com.example.tlover.domain.scrap.dto.ScrapCountResponse;
import com.example.tlover.global.dto.ResponseDto;

public interface ScrapService {

    ScrapCountResponse getScrapCount(Long diaryId);
    ResponseDto changeScrap(ScrapChangeRequest scrapChangeRequest);

}
