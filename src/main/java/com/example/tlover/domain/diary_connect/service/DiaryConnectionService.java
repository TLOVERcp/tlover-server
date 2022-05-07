package com.example.tlover.domain.diary_connect.service;

import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;

public interface DiaryConnectionService {
    DiaryInquiryResponse getDiaryDetails(Long diaryId, Long userId);
}
