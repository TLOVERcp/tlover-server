package com.example.tlover.domain.diary.service;

import com.example.tlover.domain.diary.dto.*;
import com.example.tlover.domain.diary.entity.Diary;

import java.util.List;

public interface DiaryService {

    List<DiaryInquiryResponse> getDiary();

    CreateDiaryResponse createDiary(CreateDiaryRequest createDiaryRequest, String loginId);

    DeleteDiaryResponse deleteDiary(Long diaryId, String loginId);

    Diary modifyDiary(ModifyDiaryRequest modifyDiaryRequest, String loginId);

    Diary getDiaryByDiaryId(Long diaryId);

    DiaryLikedChangeResponse diaryLikedChange(Long diaryId , String loginId);

    DiaryLikedViewsResponse getDiaryViews(Long diaryId);

    List<DiaryInquiryResponse> getGoingDiary();

    List<DiaryPreferenceResponse> getDiaryPreference(String loginId);
}
