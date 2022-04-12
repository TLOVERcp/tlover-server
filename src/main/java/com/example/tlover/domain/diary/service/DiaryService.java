package com.example.tlover.domain.diary.service;

import com.example.tlover.domain.diary.dto.CreateDiaryRequest;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.plan.entity.Plan;

public interface DiaryService {
    Diary createDiary(CreateDiaryRequest createDiaryRequest, String loginId);

    Diary deleteDiary(Long diaryId, String loginId);
}

