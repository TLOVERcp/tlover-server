package com.example.tlover.domain.diary.service;

import com.example.tlover.domain.diary.dto.CreateDiaryRequest;
import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.entity.Diary;

import java.util.List;

public interface DiaryService {

    List<DiaryInquiryResponse> getDiary();
    Diary createDiary(CreateDiaryRequest createDiaryRequest, String loginId);
}
