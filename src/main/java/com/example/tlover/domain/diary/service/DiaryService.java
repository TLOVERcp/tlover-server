package com.example.tlover.domain.diary.service;

import com.example.tlover.domain.diary.dto.*;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.global.dto.PaginationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiaryService {

    CreateDiaryResponse createDiary(CreateDiaryRequest createDiaryRequest, String loginId);

    DeleteDiaryResponse deleteDiary(Long diaryId, String loginId);



    Diary modifyDiary(ModifyDiaryRequest modifyDiaryRequest, String loginId);

    Diary getDiaryByDiaryId(Long diaryId);

    DiaryLikedChangeResponse diaryLikedChange(Long diaryId , String loginId);

    DiaryLikedViewsResponse getDiaryViews(Long diaryId);

    void completeDiary(String loginId, Long planId, Long diaryId);

    PaginationDto<List<DiaryInquiryByLikedRankingResponse>> getDiaryByLikedRanking(Pageable pageable);


    List<DiaryPreferenceResponse> getDiaryPreference(String loginId);

    List<MyDiaryListResponse> getDiaryList(String loginId);

    List<MyDiaryListResponse> getAcceptDiaryList(String loginId);

    UpdateDiaryStatusResponse updateDiaryEditing(String loginId, Long diaryId);

    List<DiaryWeatherResponse> getDiaryWeather(String loginId);

    CreateDiaryFormResponse getCreateDiaryForm(Long planId, String loginId);

    ModifyDiaryFormResponse getModifyDiaryForm(Long diaryId, String loginId);

    PaginationDto<List<DiaryMyScrapOrLikedResponse>> getDiaryMyLiked(Pageable pageable , Long userId);

    PaginationDto<List<DiaryMyScrapOrLikedResponse>> getDiaryMyScrap(Pageable pageable, Long userId);

    DiaryLikedOrNotResponse getDiaryLikedOrNot(DiaryLikedOrNotRequest diaryLikedOrNotRequest, String loginId);
}
