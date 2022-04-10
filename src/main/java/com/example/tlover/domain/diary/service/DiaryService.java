package com.example.tlover.domain.diary.service;

import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.entity.Diary;

import java.util.List;

import com.example.tlover.domain.diary.dto.CreateDiaryRequest;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.plan.entity.Plan;

public interface DiaryService {

    List<DiaryInquiryResponse> getDiary();
    Diary createDiary(CreateDiaryRequest createDiaryRequest, String loginId);
}
